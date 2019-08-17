package msz.myapplication.ui.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import msz.myapplication.R;

/**
 * @Title: MyCallback
 * @Package msz.myapplication.ui.view
 * @Description:
 * @Author: msz
 * @Mail: 2431098657@qq.com
 * @Date: 2017/3/31 0:36
 */
public class MyCallback implements ActionMode.Callback {
    private Context mContext;
    private String mText;

    public MyCallback(Context context,String string){
        mContext=context;
        mText=string;
    }
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_text_actionmode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.copy_text){
            ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(mText);
            Toast.makeText(mContext,"已经复制到剪切板",Toast.LENGTH_SHORT).show();
            actionMode.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }
}
