package zz.itcast.jiujinhui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;

public class MyTixiandetail extends BaseActivity {
	@ViewInject(R.id.name)
	private TextView nameTextView;
	@ViewInject(R.id.phonenumber)
	private TextView phonenumberTextView;
	@ViewInject(R.id.money)
	private TextView moneyTextView;
	@ViewInject(R.id.operatetime)
	private TextView operatetimeTextView;
	@ViewInject(R.id.shuoming)
	private TextView shuoming;
	private SharedPreferences sp;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	private String name;
	private String phonenumber2;
	private String moneny;
	private String state;
	private String oktime;
	private String msg;
@ViewInject(R.id.tixian_jieguo)
private RelativeLayout tixian_jieguo;
	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.mytixian_detail;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("提现详情");
		sp = getSharedPreferences("user", 0);
		Bundle bundle = getIntent().getExtras();
		name = bundle.getString("name");
		phonenumber2 = bundle.getString("phonenumber");
		moneny = bundle.getString("moneny");
		state = bundle.getString("state");
		oktime = bundle.getString("operatetime");
		msg = bundle.getString("msg");
		nameTextView.setText(name);
		phonenumberTextView.setText(phonenumber2);
		moneyTextView.setText(moneny);
		if ("0".equals(state)) {
			// 提现成功标识
			operatetimeTextView.setText(oktime);

		}else if ("2".equals(state)) {
			shuoming.setVisibility(View.VISIBLE);
			shuoming.setText("酒币提现失败原因: 支付失败,NAME_MISMATCH,真实姓名不一致.");
			shuoming.setTextColor(Color.RED);
			tixian_jieguo.setVisibility(View.GONE);
		}else if ("1".equals(state)) {
			tixian_jieguo.setVisibility(View.GONE);
			shuoming.setVisibility(View.VISIBLE);
			shuoming.setText("  提现处理中，请稍后...");
		}

	}

}
