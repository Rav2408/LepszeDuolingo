//package pl.edu.pb.lepszeduolingo.builder;
//
//import android.content.Context;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import pl.edu.pb.lepszeduolingo.rest.IVolley;
//import pl.edu.pb.lepszeduolingo.rest.VolleyRequest;
//
//public class CategoryJsonBuilder implements JSONDataBuilder{
//
//    private static final String URL = "http://34.118.90.148:8090/api/";
//    private Context context;
//    JSONArray jsonArray;
//    JSONObject jsonObject;
//
//    public CategoryJsonBuilder(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public JSONDataBuilder create() {
//        jsonObject = new JSONObject();
//        return this;
//    }
//
//    @Override
//    public JSONArray updateData() {
//        pullData();
//        return jsonArray;
//    }
//
//    private void pullData(){
//        VolleyRequest.getInstance(context, new IVolley() {
//            @Override
//            public void onResponse(JSONArray jsonArray) {
//                jsonArray=jsonArray;
//            }
//        }).getRequest(URL +"category");
//    }
//}
