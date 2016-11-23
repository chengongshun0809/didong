package zz.itcast.jiujinhui.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import zz.itcast.jiujinhui.R;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReChargeActivity extends BaseActivity {

	@ViewInject(R.id.paytype_of_weixin)
	private LinearLayout paytype_of_weixin;
	@ViewInject(R.id.chongzhi)
	private RelativeLayout chongzhi;
	@ViewInject(R.id.paytype_of_weixin_ck)
	private CheckBox weixinCK;
	@ViewInject(R.id.paytype_of_zhifubao_ck)
	private CheckBox zhifubaoCK;

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

	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	
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
	}

	

	@OnClick(R.id.paytype_of_weixin)
	public void wx(View v) {
		if (zhifubaoCK.isChecked()) {
			zhifubaoCK.setChecked(false);
		}
		if (!weixinCK.isChecked()) {
			weixinCK.setChecked(true);
		}

	}

	@OnClick(R.id.paytype_of_zhifubao)
	public void zfb(View v) {
		if (weixinCK.isChecked()) {
			weixinCK.setChecked(false);
		}
		if (!zhifubaoCK.isChecked()) {
			zhifubaoCK.setChecked(true);
		}

	}

	@OnClick(R.id.paytype_of_weixin_ck)
	public void wxck(View v) {
		if (zhifubaoCK.isChecked()) {
			zhifubaoCK.setChecked(false);
		}
		if (!weixinCK.isChecked()) {
			weixinCK.setChecked(true);
		}
	}

	@OnClick(R.id.paytype_of_zhifubao_ck)
	public void zfbck(View v) {
		if (weixinCK.isChecked()) {
			weixinCK.setChecked(false);
		}
		if (!zhifubaoCK.isChecked()) {
			zhifubaoCK.setChecked(true);
		}
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

			break;
		case R.id.yiqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.VISIBLE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);

			break;
		case R.id.liangqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.VISIBLE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);

			break;
		case R.id.wuqian:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.VISIBLE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);

			break;
		case R.id.yiwan:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.VISIBLE);
			iv_drink_checked_sanwan.setVisibility(v.GONE);

			break;
		case R.id.sanwan:
			iv_drink_checked_wubai.setVisibility(v.GONE);
			iv_drink_checked_yiqian.setVisibility(v.GONE);
			iv_drink_checked_liangqian.setVisibility(v.GONE);
			iv_drink_checked_wuqian.setVisibility(v.GONE);
			iv_drink_checked_yiwan.setVisibility(v.GONE);
			iv_drink_checked_sanwan.setVisibility(v.VISIBLE);

			break;
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}

	}
}
