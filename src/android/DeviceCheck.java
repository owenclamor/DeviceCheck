  package owen.dev.devicecheck;

  import android.app.ActivityManager;
  import android.content.Context;
  import android.os.Build;

  import org.apache.cordova.CallbackContext;
  import org.apache.cordova.CordovaPlugin;
  import org.json.JSONArray;
  import org.json.JSONException;

  import java.text.DecimalFormat;

/**
 * This class echoes a string called from JavaScript.
 */
public class DeviceCheck extends CordovaPlugin {
  private static String GET_CPU = "getCPU";
  private static String GET_RAM = "getRAM";
  private static String GET_DEVICENAME = "getDeviceName";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if(GET_CPU.equals(action)){
          String cpu = args.getString(0);
          getCPU(cpu, callbackContext);
        }else if(GET_RAM.equals(action)){
          String ram = args.getString(0);
          getRAM(ram, callbackContext);
        }else if(GET_DEVICENAME.equals(action)){
          String deviceName = args.getString(0);
          getDeviceName(deviceName, callbackContext);
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    public boolean getCPU(String result, CallbackContext callbackContext){
      //Check if CPU supports 64 bit -> if no then false
      //Check if CPU contains arm -> if no then false

      if(Build.SUPPORTED_64_BIT_ABIS.length == 0 || !checkCPU()){
        callbackContext.error("false");
        return false;
      }
      callbackContext.success("true");
      return true;
    }

    private boolean checkCPU(){
      for(String abi: Build.SUPPORTED_ABIS){
        if(abi.contains("arm")){
          return true;
        }
      }
      return false;
    }
    public boolean getRAM(String test, CallbackContext callbackContext){

      // Context context = cordova.getActivity().getApplicationContext();
      
      ActivityManager activityManager = (ActivityManager) cordova.getActivity().getSystemService(Context.ACTIVITY_SERVICE);

      ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

      activityManager.getMemoryInfo(memoryInfo);

      DecimalFormat twoDecimalForm = new DecimalFormat("#.##");

    

      long totalMemory = memoryInfo.totalMem;
      double totalMemoryGB = (double) totalMemory / (1024 * 1024 * 1024);
      double reqMemory = Double.valueOf(test);
      String formatMemory = twoDecimalForm.format(totalMemoryGB);

    

      if(totalMemoryGB >= reqMemory){
        //Memory is less than 8gb
        callbackContext.success("true");
      return true;
      }
      callbackContext.success("false");
      return false;

    }

    public boolean getDeviceName(String test, CallbackContext callbackContext){
        //GET DEVICE NAME
        String DeviceName = Build.MODEL;

        String[] AcceptedDevices = {
                "Xiaomi Redmi Nord N200 5G",
                "Xiaomi Redmi Note 10",
                "Realme 8",
                "Sony Xperia XZ2 Compact",
                "Sony Xperia XZ3",
                "Sony Xperia XZ2",
                "LG G7 ThinQ",
                "Google Pixel 3 XL",
                "Google Pixel 3"
        };

        String[] RejectDevices = {
                "Oppo Realme 1",
                "LG G6",
                "Sony Xperia 10 plus",
                "Nokia 6.2",
                "Nokia 6.1 Plus",
                "Asus Zenfone Max Pro (M1)",
                "Samsung Galaxy C9 Pro",
                "Vivo X9",
                "Samsung Galaxy C9 Pro",
                "Vivo X9",
                "Samsung Galaxy S6",
                "Sony Xperia XA1 Ultra",
                "Sony Xperia XA1 Plus",
                "Vivo V5s",
                "Huawei Mate SE",
                "Huawei Mate 10 Lite",
                "Huawei P20 Lite",
                "Huawei Honor 7X",
                "Huawei P10 Lite",
                "Doogee Mix",
                "Huawei Nova 2i"
        };

        //Search Rejected Devices
        boolean foundRejected = false;
        for(int i = 0; i < RejectDevices.length; i++){
            if(RejectDevices[i].equals(DeviceName)){
                foundRejected = true;
                callbackContext.error("false");
                return false;
                
            }
        }

        //Search Accepted Devices
        boolean foundAccepted = false;
        for(int i = 0; i < AcceptedDevices.length; i++) {
            if (AcceptedDevices[i].equals(DeviceName)) {
                foundAccepted = true;
                callbackContext.success("true");
                return true;
                
            }
        }
        callbackContext.success("true");
        return false;
    }
  }
