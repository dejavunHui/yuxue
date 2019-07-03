package com.example.androidmvp.mvp.residemenu.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.androidmvp.R;
import com.example.androidmvp.mvp.base.BaseActivity;
import com.example.androidmvp.mvp.entity.UserResult;
import com.example.androidmvp.mvp.entity.db.Remark;
import com.example.androidmvp.mvp.entity.db.ShowPage;
import com.example.androidmvp.mvp.residemenu.presenter.ResidePresenter;
import com.example.androidmvp.mvp.residemenu.view.BaseUserShowView;
import com.example.androidmvp.mvp.show.activity.CreateRemarkActivity;
import com.example.androidmvp.mvp.show.activity.CreateShowPageActivity;
import com.example.androidmvp.mvp.show.adapter.ReCyclerAdapter;
import com.example.androidmvp.widget.MyRemarkView;
import com.example.androidmvp.widget.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.androidmvp.mvp.show.fragment.ShowFragment.REQUEST_CODE;

public class UserShowActivity extends BaseActivity implements BaseUserShowView {
    private static final String TAG = "UserShowActivity";
    private static boolean isHeart = false;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ReCyclerAdapter reCyclerAdapter;
    private UserResult user;
    private RecyclerView.LayoutManager manager;


    private ResidePresenter presenter;

    @Override
    protected void anything() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_user_show);
    }

    @Override
    protected void findViewById() {
        recyclerView = findViewById(R.id.user_show_item);
        swipeRefreshLayout = findViewById(R.id.user_show_swiperefreshlayout);
        reCyclerAdapter = new ReCyclerAdapter(getActivityContext(), new ArrayList<ShowPage>());
        Bundle bundle = getIntent().getExtras();
        user = (UserResult) bundle.getSerializable("user");
        presenter = new ResidePresenter(this);
        manager = new LinearLayoutManager(this);

    }

    @Override
    protected void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadOnesShowPages(user.getUsername());
                swipeRefreshLayout.setRefreshing(false);
                showMessage("刷新成功");
            }
        });

        reCyclerAdapter.setOnItemClickListener(new OnItemClickListener());

        reCyclerAdapter.setOnRemarkItemListener(new RemarkItemClickListener());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(reCyclerAdapter);
    }

    @Override
    protected void processLogic() {
        presenter.loadOnesShowPages(user.getUsername());
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .ImagePickerTheme,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    class RemarkItemClickListener implements MyRemarkView.ClickListener {
        @Override
        public void onClickItem(View view, Remark mark) {
            editRemark(mark);
        }

        public void editRemark(Remark remark) {
            Bundle bundle = new Bundle();
            bundle.putString("from", user.getUsername());
            bundle.putString("to", remark.getFrom());
            bundle.putString("page", remark.getPage());
            Intent intent = new Intent(UserShowActivity.this, CreateRemarkActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    class OnItemClickListener implements ReCyclerAdapter.OnItemClickListener {
        @Override
        public void onItemClick(View view, List<ShowPage> showPageResults, int position) {
            //点击recycleview item 事件
            switch (view.getId()) {
                case R.id.dianzan:
                    clickHeart(view);
                    break;
                case R.id.show_remark:
                    editRemark(showPageResults.get(position));
                    break;
                case R.id.edit_remark:
                    editRemark(showPageResults.get(position));
                    break;
                default:
                    deleteShowpage(showPageResults.get(position));
                    break;

            }
        }

        public void deleteShowpage(final ShowPage page){
            List<String> names = new ArrayList<>();
            names.add("删除");
            names.add("取消");
            showDialog(new SelectDialog.SelectDialogListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0: // 删除
                            presenter.deleteShowpage(String.valueOf(page.getId()));
                            presenter.loadOnesShowPages(user.getUsername());
                            break;
                        case 1:
                            break;
                        default:
                            break;
                    }

                }
            }, names);
        }

        private void clickHeart(View view) {
            if (!isHeart) {
                ((ImageButton) view).setImageResource(R.drawable.hearclick);
                isHeart = true;
            } else {
                ((ImageButton) view).setImageResource(R.drawable.ic_heart_fill);
                isHeart = false;
            }
        }

        private void editRemark(ShowPage page) {
            Bundle bundle = new Bundle();
            bundle.putString("from", user.getUsername());
//            bundle.putString("to",null);
            bundle.putString("page", String.valueOf(page.getId()));
            Intent intent = new Intent(UserShowActivity.this, CreateRemarkActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadShowpages(List<ShowPage> data) {
        Log.d(TAG, "loadShowpages: " + data.size());
        reCyclerAdapter.setShowPageResults(data);
        reCyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUser(UserResult user) {

    }
}
