package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.Arith;
import zz.itcast.jiujinhui.res.NetUtils;

public class WoyaorengouActivity extends BaseActivity {

	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	private SharedPreferences sp;
	private String unionidString;

	@ViewInject(R.id.jiujiaoname)
	private TextView jiujiaoname;
	@ViewInject(R.id.person_assets)
	private RelativeLayout person_assets;

	@ViewInject(R.id.hscrollview)
	private HorizontalScrollView hscrollview;
	@ViewInject(R.id.ll_scroll)
	private LinearLayout ll_scroll;

	@ViewInject(R.id.jiubi)
	private TextView jiubi;
	@ViewInject(R.id.zongzichan)
	private TextView zongzichan;

	@ViewInject(R.id.yitihuo)
	private TextView yitihuo;
	@ViewInject(R.id.xiaji)
	private TextView xiaji;

	@ViewInject(R.id.reward)
	private TextView reward;

	@ViewInject(R.id.ren_price)
	private TextView ren_price;
	@ViewInject(R.id.ren_owner)
	private TextView ren_owner;

	@ViewInject(R.id.ren_leftday)
	private TextView ren_leftday;

	@ViewInject(R.id.rb_buy_rengou)
	private LinearLayout rb_buy_rengou;

	@ViewInject(R.id.rb_tihuo_rengou)
	private LinearLayout rb_tihuo_rengou;

	boolean stopThread = false;
	private String dgid;
	private String name;
	private String maindealtermString;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				UpdateUI();
				break;
			case 2:
				UpdatehscrollviewUI();
				break;
			default:
				break;
			}

		};

	};
	private double jiubiString;
	private double rengou_price;
	private DecimalFormat df;
	private String dealString;
	private String owner;
	private String zongString;
	private String yitihuoString;
	private String yitrans;
	private String xia;
	private double record;
	private String stock;

	protected void UpdateUI() {
		// TODO Auto-generated method stub
		jiujiaoname.setText(name);
		jiubi.setText(df.format(jiubiString / 100));
		zongzichan.setText(zongString);
		yitihuo.setText(yitihuoString);
		xiaji.setText(xia);
		reward.setText(df.format(record / 100));
		ren_price.setText(df.format(rengou_price / 100));
		ren_owner.setText(owner);
		ren_leftday.setText(maindealtermString);
	}

	// 实现滚动线程
	int j = 0;
	private ImageView addImageView;
	private ImageView jianImageView;
	private TextView counTextView;
	private TextView price;
	private Button dialog_cancel;
	private Button dialog_ok;

	protected void UpdatehscrollviewUI() {
		// TODO Auto-generated method stub
		// 总宽度
		int totaloff = hscrollview.getMeasuredWidth();
		// 判断宽度
		int off = ll_scroll.getMeasuredWidth();
		int fax = totaloff / off;

		if (j < 7) {
			hscrollview.scrollBy(off, 0);
			j = j + 1;
		} else {
			j = 0;
			hscrollview.scrollBy(-7 * off, 0);
		}

		handler.removeMessages(2);
		handler.sendEmptyMessageDelayed(2, 2000);
	}

	@Override
	public void initData() {
		handler.sendEmptyMessageDelayed(2, 3000);
		unionidString = sp.getString("unionid", null);
		Bundle bundle = getIntent().getExtras();
		name = bundle.getString("name");
		dgid = bundle.getString("dgid");
		maindealtermString = bundle.getString("maindealterm");
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {
					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/dealsubscribe?"
							+ "&dgid=" + dgid + "&unionid=" + unionidString;

					try {
						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");

						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							// JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("我靠快快快快快快快", jsonObject.toString());

							//Log.e("hahahhahh", infojson);
							parseJson(infojson);
							stopThread = true;

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (iStream != null) {
							try {
								iStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}

				}
			}
		}).start();

	}

	protected void parseJson(String json) {
		// TODO Auto-generated method stub
		try {
			df = new DecimalFormat("#0.00");
			JSONObject jObject = new JSONObject(json);
			jiubiString = jObject.getDouble("income");
			rengou_price = jObject.getDouble("realprice");
			dealString = jObject.getString("dealgood");

			JSONObject jObject2 = new JSONObject(dealString);
			owner = jObject2.getString("owner");

			// 认购总资产
			String dealdataString = jObject.getString("dealdata");
			JSONObject jObject3 = new JSONObject(dealdataString);

			zongString = jObject3.getString("subnum");
			yitihuoString = jObject3.getString("consumenum");
			yitrans = jObject3.getString("buybacknum");
			xia = jObject3.getString("downnum");
			record = jObject3.getDouble("downaward");
			stock = jObject3.getString("stock");
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		person_assets.setOnClickListener(this);
		rb_buy_rengou.setOnClickListener(this);
		rb_tihuo_rengou.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("认购");
		sp = getSharedPreferences("user", 0);
	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.woyaorengou_activity;
	}
	int buy_count=1;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_back:
			finish();
			break;
		case R.id.person_assets:
			Intent intent = new Intent(WoyaorengouActivity.this,
					TradeRecordActivity.class);
			startActivity(intent);

			break;
		case R.id.rb_buy_rengou:// 认购
			showRengou_buy();
			break;
		case R.id.rb_tihuo_rengou:
			showTihuo();
			break;
		default:
			break;
		}

	}

	TextWatcher textWatcher=new TextWatcher() {
		private CharSequence charSequence;
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			String countString=counTextView.getText().toString().trim();
			price.setText(""+Arith.mul(rengou_price/100, Double.parseDouble(countString)));
			
			
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
	private ImageView add_tihuo;
	private ImageView reduce_tihuo;
	private TextView num;
	private void showRengou_buy() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = (View) inflater.inflate(R.layout.rengou_buy, null);
		addImageView = (ImageView) view.findViewById(R.id.add);
		jianImageView = (ImageView) view.findViewById(R.id.jian);
		counTextView = (TextView) view.findViewById(R.id.count);
		price = (TextView) view.findViewById(R.id.price);
		dialog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
		dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
		final AlertDialog builder = new AlertDialog.Builder(this).create();
		builder.setView(view, 0, 0, 0, 0);
		builder.setCancelable(false);
		builder.show();
		price.setText(df.format(rengou_price/100));
		counTextView.addTextChangedListener(textWatcher);
		//price.addTextChangedListener(textWatcher);
		
		
		addImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buy_count++;
				if (buy_count<=100) {
					counTextView.addTextChangedListener(textWatcher);
					counTextView.setText(buy_count + "");
					
				}else {
					Toast.makeText(getApplicationContext(), "最大认购量不能超过100", 0).show();
					buy_count=100;
				}
				

			}
		});
		
		jianImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (buy_count>1) {
					buy_count--;
					counTextView.addTextChangedListener(textWatcher);
					counTextView.setText(buy_count+"");
				}else {
					buy_count=1;
					Toast.makeText(getApplicationContext(), "最小认购量不能小于1", 0).show();
				}
			}
		});
		dialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.dismiss();
				buy_count=1;
				
			}
		});
		dialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.e("zhzh", price.getText().toString().trim());
				
			}
		});

	}
	int tihuo_count=1;
    //提货
	private void showTihuo() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = (View) inflater.inflate(R.layout.rengou_tihuo, null);
		reduce_tihuo = (ImageView) view.findViewById(R.id.reduce_tihuo);
		add_tihuo = (ImageView) view.findViewById(R.id.add_tihuo);
		num = (TextView) view.findViewById(R.id.num);
		dialog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
		dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
		final AlertDialog builder = new AlertDialog.Builder(this).create();
		builder.setView(view, 0, 0, 0, 0);
		builder.setCancelable(false);
		builder.show();
		dialog_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.dismiss();
				tihuo_count=1;
				
			}
		});
		dialog_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("tihuo_ok", num.getText().toString().trim());
				
				
			}
		});
		add_tihuo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tihuo_count++;
				num.setText(tihuo_count+"");
			}
		});
		reduce_tihuo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tihuo_count>1) {
					tihuo_count--;
					num.setText(tihuo_count+"");
				}else {
					Toast.makeText(getApplicationContext(), "最小提货量不能小于1", 0).show();
					tihuo_count=1;
					
				}
			
			}
		});
		
		
		
		
		
		
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopThread = true;
		handler.removeMessages(1);
		handler.removeMessages(2);
	}
}
