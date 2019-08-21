package adapters;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import constants.ApplicationConstants;
import fragments.DeviceFragment;
import solaps.com.chargebot.R;
import util.UtilClass;

/**
 * Created by prasadsawant on 11/21/16.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private static final String TAG = DeviceAdapter.class.getName();

    private String[] devices;
    private Context context;
    private TextView tvDeviceName, tvBatteryPercentage;
    private FragmentTransaction fragmentTransaction;


    public DeviceAdapter(String[] devices, Context context, FragmentTransaction fragmentTransaction) {
        this.devices = devices;
        this.context = context;
        this.fragmentTransaction = fragmentTransaction;
    }

    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View deviceView = inflater.inflate(R.layout.row_devices, parent, false);

        ViewHolder viewHolder = new ViewHolder(deviceView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DeviceAdapter.ViewHolder holder, final int position) {
        String device = devices[position];

        tvDeviceName = holder.tvDeviceName;
        tvDeviceName.setText(device);
        UtilClass.setCustomFont(context, tvDeviceName, context.getString(R.string.font_lato_regular));

        tvBatteryPercentage = holder.tvBatteryPercentage;
        UtilClass.setCustomFont(context, tvBatteryPercentage, context.getString(R.string.font_lato_regular));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.frame_layout, new DeviceFragment());
                fragmentTransaction.addToBackStack(ApplicationConstants.FRAGMENT_DEVICE);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return devices.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDeviceName, tvBatteryPercentage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDeviceName = (TextView) itemView.findViewById(R.id.tv_device_name);
            tvBatteryPercentage = (TextView) itemView.findViewById(R.id.tv_battery_percentage);
        }
    }
}
