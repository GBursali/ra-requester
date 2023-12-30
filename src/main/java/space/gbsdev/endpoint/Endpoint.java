package space.gbsdev.endpoint;

import com.google.gson.JsonObject;

import io.restassured.response.Response;
import space.gbsdev.utils.JSONUtils;
import space.gbsdev.utils.MethodType;

import java.util.Map;

public class Endpoint {
    private MethodType type = MethodType.GET;
    private final EndpointBase base;
    private String url;

    public static Endpoint fromBase(EndpointBase base){
        return new Endpoint(base);
    }

    protected Endpoint(EndpointBase base){
        this.base = base;
    }

    public static Endpoint fromJson(EndpointBase endpointBase, String json) {
        var instance = new Endpoint(endpointBase);
        JsonObject object = endpointBase.jsonify(json).getAsJsonObject();
        JSONUtils.doIfJsonHasObject(object,"settings",instance::pullSettings);
        JSONUtils.doIfJsonHasObject(object,"params",instance::addParam);
        JSONUtils.doIfJsonHasObject(object,"body",body->instance.setBody(body.toString()));
        return instance;
    }

    private void setBody(String body) {
        this.base.getRawRequest().body(body);
    }

    public void addParam(Map<String,String> params) {
        this.base.getRawRequest().params(params);
    }
    private void addParam(JsonObject params) {
        addParam(JSONUtils.objToStringMap(params));
    }

    private void pullSettings(JsonObject settings) {
        JSONUtils.doIfJsonHasString(settings,"path",this::withPath);
        JSONUtils.doIfJsonHasString(settings,"method",this::withType);
    }

    public Endpoint withPath(String path){
        this.url = path;
        return this;
    }
    public Endpoint withType(MethodType type){
        this.type = type;
        return this;
    }
    public Endpoint withType(String type){
        this.type = MethodType.valueOf(type);
        return this;
    }

    public Response send() {
        return base.getRawRequest()
                .request(type.toString(),url)
                .thenReturn();
    }
}
