package br.com.code85.lifepower.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import br.com.code85.lifepower.R;

/**
 * Created by Daniel on 27/01/2017.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<String> listGroup;
    private HashMap<String,List<String>> listData;
    private LayoutInflater inflater;

    public ExpandableAdapter(Context context, List<String> listGroup, HashMap<String, List<String>> listData){
        this.listGroup = listGroup;
        this.listData = listData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(listGroup.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.header_expandable_listview, null);
            holder = new ViewHolderGroup();
            convertView.setTag(holder);

            holder.tvGroup = (TextView) convertView.findViewById(R.id.tvGroup);
        } else{
            holder = (ViewHolderGroup) convertView.getTag();
        }

        holder.tvGroup.setText(listGroup.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition  , boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItem holder;
        String val = (String) getChild(groupPosition,childPosition);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_expandable_listview, null);
            holder = new ViewHolderItem();
            convertView.setTag(holder);

            holder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        } else{
            holder = (ViewHolderItem) convertView.getTag();
        }

        holder.tvItem.setText(val);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true; //Clique nas linhas
    }

    class ViewHolderGroup{
        TextView tvGroup;
    }

    class ViewHolderItem{
        TextView tvItem;
    }
}
