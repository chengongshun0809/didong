package zz.itcast.jiujinhui.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;

import zz.itcast.jiujinhui.R;
import android.content.SharedPreferences;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

public class ReChargeActivity extends BaseActivity {

	@ViewInject(R.id.chongzhi)
	private RelativeLayout chongzhi;

	@ViewInject(R.id.wubai)
	private RelativeLayout wubai;
	@ViewInject(R.id.yiqian)
	private RelativeLayout yiqian;
	@ViewInject(R.id.liangqian)
	private RelativeLayout liangqian;
	@ViewInject(R.id.wuqian)
	private RelativeLayout wuqian;
	@ViewInject(R.id.yiwan)
	private RelativeLayout yiwan;
	@ViewInject(R.id.sanwan)
	private RelativeLayout sanwan;

	// 小对勾
	@ViewInject(R.id.iv_drink_checked_wubai)
	private ImageView iv_drink_checked_wubai;
	@ViewInject(R.id.iv_drink_checked_yiqian)
	private ImageView iv_drink_checked_yiqian;
	@ViewInject(R.id.iv_drink_checked_liangqian)
	private ImageView iv_drink_checked_liangqian;
	@ViewInject(R.id.iv_drink_checked_wuqian)
	private ImageView iv_drink_checked_wuqian;
	@ViewInject(R.id.iv_drink_checked_yiwan)
	private ImageView iv_drink_checked_yiwan;
	@ViewInject(R.id.iv_drink_checked_sanwan)
	private ImageView iv_drink_checked_sanwan;
	@ViewInject(R.id.other_moneny)
	private EditText other_moneny;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.phonenumber)
	private TextView phonenumber;

	private SharedPreferences sp;

	// 圆形图片
	@ViewInject(R.id.circleImabeView)
	private zz.itcast.jiujinhui.view.CircleImageView circleImabeView;

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.recharge_activity;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("充值");
		tv__title.setTextSize(22);
		sp = getSharedPreferences("user", 0);
		// 此方法不完善，等绑定手机号后自动获取绑定的手机号
		String pnumber = sp.getString("mobile", null);
		phonenumber.setText(pnumber);

		sp = getSharedPreferences("user", 0);
		String headimgurl = sp.getString("headimg", null);
		Picasso.with(this).load(headimgurl).into(circleImabeView);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		wubai.setOnClickListener(this);
		yiqian.setOnClickListener(this);
		liangqian.setOnClickListener(this);
		wuqian.setOnClickListener(this);
		yiwan.setOnClickListener(this);
		sanwan.setOnClickListener(this);
		tv_back.setOnClickListener(this);
		other_moneny.setOnTouchListener(new OnTouchListener() {
			// 按住和松开的标识
			int touch_flag = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				touch_flag++;
				if (touch_flag == 2) {
					iv_drink_checked_wubai.setVisibility(v.GONE);
					iv_drink_checked_yiqian.setVisibility(v.GONE);
					iv_drink_checked_liangqian.setVisibility(v.GONE);
					iv_drink_checked_wuqian.setVisibility(v.GONE);
					iv_drink_checked_yiwan.setVisibility(v.GONE);
					iv_drink_checked_sanwan.setVisibility(v.GONE);
					other_moneny.setFocusable(true);
					other_moneny.setFocusableInTouchMode(true);
					other_moneny.requestFocus();
					touch_flag=0;
				}

				return false;
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wubai:
			iv_drink_checked_wubai.setVisibility(v.VISIBLE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.yiqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.VISIBLE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.liangqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.VISIBLE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.wuqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.VISIBLE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		case R.id.yiwan:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.VISIBLE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);

			break;
		case R.id.sanwan:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.VISIBLE);
			other_moneny.setFocusable(false);
			other_moneny.setFocusableInTouchMode(false);
			break;
		/*
		 * case R.id.other_moneny:
		 * 
		 * 
		 * 
		 * break;
		 */
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}

	}
}
