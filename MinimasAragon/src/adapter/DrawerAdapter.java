package adapter;

import java.util.ArrayList;

import com.minimasaragon.R;

import model.DrawerModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

 
public class DrawerAdapter extends BaseAdapter {
     
    private Context context;
    private ArrayList<DrawerModel> navDrawerItems;
    
    
    public DrawerAdapter(Context context, ArrayList<DrawerModel> navDrawerItems ){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
        
        
    }
 
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }
 
    @Override
    public Object getItem(int position) {      
        return navDrawerItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_item, null);
        }
        ImageView imView=(ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);      
        
        
        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
       imView.setImageResource(navDrawerItems.get(position).getIcon());
       
         
        // displaying count
        // check whether it set visible or not
       
        if(navDrawerItems.get(position).getCounterVisibility()){
            txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
            // hide the counter view
            txtCount.setVisibility(View.GONE);
        }
        
        return convertView;
    }
 
}