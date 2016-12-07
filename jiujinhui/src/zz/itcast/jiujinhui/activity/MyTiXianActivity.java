package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.PrivateCredentialPermission;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyTiXianActivity extends BaseActivity {

	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	private SharedPreferences sp;
	private String incomeString;
	private String mobileString;
	private String unionString;

	@ViewInject(R.id.mobile)
	private TextView mobile;
	@ViewInject(R.id.wine_money)
	private TextView wine_money;
	@ViewInject(R.id.total_tixian)
	private TextView total_tixian;
	@ViewInject(R.id.shouxufei)
	private TextView shouxufei;
	@ViewInject(R.id.msg_info)
	private TextView msg_info;
	@ViewInject(R.id.ll_kou_first)
	private LinearLayout ll_kou_first;
	@ViewInject(R.id.ll_kou_second)
	private LinearLayout ll_kou_second;
	@ViewInject(R.id.woyaotixian)
	private RelativeLayout woyaotixian;
	@ViewInject(R.id.woyaotixian_hui)
	private RelativeLayout woyaotixian_hui;
	@ViewInject(R.id.count)
	public EditText countEditText;
	@ViewInject(R.id.name)
	public EditText nameEditText;

	@Override
	public void initData() {
		Bundle bundle = getIntent().getExtras();

		incomeString = bundle.getString("money");
		mobileString = bundle.getString("mobile");
		unionString = sp.getString("unionid", null);
		mobile.setText(mobileString);
		jiubi_total = Double.parseDouble(incomeString);
		wine_money.setText(incomeString);

		woyaotixian_hui.setVisibility(View.GONE);
		woyaotixian.setVisibility(View.VISIBLE);

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("提现");
		tv__title.setTextSize(22);
		sp = getSharedPreferences("user", 0);

		nameEditText.addTextChangedListener(textWatcher);
		countEditText.addTextChangedListener(textWatcher);
		// 实际钱

	}

	private TextWatcher textWatcher = new TextWatcher() {
		private CharSequence charSequence;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			charSequence = s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};
	private double countincome;
	private String countString;
	private String namString;
	private String countsString;
	private Double ticount;

	private String shouxufeiString;
	private double realmon;
	private double jiubi_total;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.total_tixian:

			countEditText.setText(incomeString);

			break;
		case R.id.woyaotixian:
			// 提现
			//woyaotixian.setEnabled(false);
			namString = nameEditText.getText().toString().trim();
			countsString = countEditText.getText().toString().trim();
			if (!TextUtils.isEmpty(namString)
					&& !TextUtils.isEmpty(countsString)) {

				ticount = Double.parseDouble(countsString);

				shouxufeiString = (ticount * 0.006) + "";
				realmon = ticount - ticount * 0.006;
				if (ticount <= jiubi_total && ticount >= 2) {
					new Thread(new Runnable() {

						private InputStream is;

						@Override
						public void run() {

							try {
								String urlpath = "https://www.4001149114.com/NLJJ/ddapp/withdraw?unionid="
										+ unionString
										+ "&fee="
										+ "realmon"
										+ "&name="
										+ namString
										+ "&mobile="
										+ mobileString;
								HttpsURLConnection conn = NetUtils
										.httpsconnNoparm(urlpath, "POST");
								// 若连接服务器成功，返回数据
								int code = conn.getResponseCode();
								if (code == 200) {

									woyaotixian.setEnabled(true);
									Intent intent = new Intent();
									Bundle bundle = new Bundle();
									// bundle.putString("money", incomeString);
									bundle.putString("count", countsString);
									bundle.putString("shouxufei",
											shouxufeiString);
									intent.putExtras(bundle);
									startActivity(intent);
									// Thread.sleep(30000);

									is.close();
								}

							} catch (Exception e) {
								// TODO: handle exception
							} finally {
								if (is != null) {
									try {
										is.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

						}

					}).start();
				} else {
					Toast.makeText(getApplicationContext(), "酒币提现数量错误，请重新输入", 0)
							.show();
					woyaotixian.setEnabled(false);
				}

			} else {
				woyaotixian_hui.setVisibility(View.VISIBLE);
				woyaotixian.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "姓名或提现酒币不能为空", 0)
						.show();
			}

			break;
		default:
			break;
		}
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.metixian_activity;
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		total_tixian.setOnClickListener(this);
	}

}
