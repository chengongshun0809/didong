package zz.itcast.jiujinhui.activity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.fragment.BuyChartFragment;
import zz.itcast.jiujinhui.fragment.EveryDayTradeRecordFragment;
import zz.itcast.jiujinhui.fragment.NowTradeRecoedFragment;
import zz.itcast.jiujinhui.fragment.SaleChartFragment;
import zz.itcast.jiujinhui.res.Arith;
import zz.itcast.jiujinhui.res.NetUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TradeServiceActivity extends BaseActivity {
	@ViewInject(R.id.scrollview)
	private zz.itcast.jiujinhui.view.MyScrollView scrollview;

	// 买入
	@ViewInject(R.id.rb_buy_service)
	private LinearLayout rb_buy_service;
	// 卖出
	@ViewInject(R.id.rb_sale_service)
	private LinearLayout rb_sale_service;

	// 提货
	@ViewInject(R.id.rb_tihuo_service)
	private LinearLayout rb_tihuo_service;
	// 转让
	@ViewInject(R.id.rb_zhuanrang_service)
	private LinearLayout rb_zhuanrang_service;
	// 个人资产
	@ViewInject(R.id.person_assets)
	private RelativeLayout person_assets;

	// 交易曲线
	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip tabs;
	@ViewInject(R.id.trade_pager)
	private ViewPager trade_pager;
	// 酒窖名字
	@ViewInject(R.id.jiujiaoname)
	private TextView jiujiaoname;

	//
	// 交易曲线
	private ArrayList<Fragment> fragmentsList1;
	// 买入卖出曲线
	@ViewInject(R.id.tabs_buysale)
	private PagerSlidingTabStrip tabs_buysale;
	@ViewInject(R.id.buy_sale_pager)
	private ViewPager buy_sale_pager;

	private ArrayList<Fragment> fragmentsList2;
	@ViewInject(R.id.hscrollview)
	private HorizontalScrollView hscrollview;
	@ViewInject(R.id.ll_scroll)
	private LinearLayout ll_scroll;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	private SharedPreferences sp;
	// 今日涨跌
	@ViewInject(R.id.total_zd)
	private TextView total_zd;
	// 交易指导价
	@ViewInject(R.id.realprice)
	private TextView realpri;
	// 酒币
	@ViewInject(R.id.jiubi)
	private TextView jiubi;
	// 总资产
	@ViewInject(R.id.subnum)
	private TextView total_assets;
	// 总收益
	@ViewInject(R.id.total_shouyi)
	private TextView total_shouyi;
	// 剩余资产
	@ViewInject(R.id.left_assets)
	private TextView left_assets;
	// 卖出中
	@ViewInject(R.id.saling)
	private TextView saling;
	@ViewInject(R.id.xiaji)
	private TextView xiaji;
	// 买入中
	@ViewInject(R.id.buying)
	private TextView buying;

	// 已成交
	@ViewInject(R.id.dealed)
	private TextView dealed;
	// 已提货
	@ViewInject(R.id.taked_goods)
	private TextView taked_goods;
	// 已转让
	@ViewInject(R.id.transed)
	private TextView transed;
	// 奖励
	@ViewInject(R.id.reward)
	private TextView reward;
	boolean stopThread = false;
	// 定义一个Handler对象
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 1:
				UpdateUI();
				scrollview.invalidate();// 定时刷新
				break;
			case 2:
				UpdatehscrollviewUI();
				// hscrollview.invalidate();
			default:
				break;
			}

		};

	};

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);

		rb_buy_service.setOnClickListener(this);
		rb_sale_service.setOnClickListener(this);
		rb_tihuo_service.setOnClickListener(this);
		rb_zhuanrang_service.setOnClickListener(this);
		person_assets.setOnClickListener(this);

	}

	// 实现滚动线程
	int j = 0;

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
		handler.sendEmptyMessageDelayed(2, 3000);
	}

	// 当前滚动距离
	int currentX = 0;

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		ViewUtils.inject(this);
		tv__title.setText("交易服务");
		// hscrollview定时滚动

		handler.sendEmptyMessageDelayed(2, 3000);

		dgid = getIntent().getStringExtra("dealdgid");
		String name = getIntent().getStringExtra("name");
		jiujiaoname.setText(name);
		// Log.e("mm", dgid);
		sp = getSharedPreferences("user", 0);
		unionid = sp.getString("unionid", null);
		// Log.e("ms我的unionID是：", unionid);

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.frag_trade_service;
	}

	// 获取到数据后要更新ui
	/*
	 * Handler mHandler = new Handler() {
	 * 
	 * public void handleMessage(android.os.Message msg) { switch (msg.what) {
	 * case 1: UpdateUI(); break;
	 * 
	 * default: break; }
	 * 
	 * };
	 * 
	 * };
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		fragmentsList1 = new ArrayList<Fragment>();
		fragmentsList1.add(new NowTradeRecoedFragment());
		fragmentsList1.add(new EveryDayTradeRecordFragment());
		fragmentsList2 = new ArrayList<Fragment>();
		fragmentsList2.add(new SaleChartFragment());
		fragmentsList2.add(new BuyChartFragment());
		trade_pager.setAdapter(new MypagerAdapter(getSupportFragmentManager(),
				fragmentsList1));
		buy_sale_pager.setAdapter(new MyBuySalepagerAdapter(
				getSupportFragmentManager(), fragmentsList2));
		tabs.setViewPager(trade_pager);
		tabs_buysale.setViewPager(buy_sale_pager);
		tabs.setShouldExpand(true);
		tabs_buysale.setShouldExpand(true);

		// 获取数据

		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {

					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/hallorder?unionid="
							+ unionid + "&dgid=" + dgid;

					try {
						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");

						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("ssssssssss", jsonObject.toString());
							parseJson(jsonObject);
							Thread.sleep(30000);

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

	protected void parseJson(JSONObject jsonObject) {
		try {
			// 用户手机号
			String phonenum = jsonObject.getString("mobile");
			// Log.e("mobile", phonenum);
			sp.edit().putString("mobile", phonenum).commit();

			income = jsonObject.getDouble("income");

			// sp.edit().putFloat("jiubi", (float) (income/100)).commit();
			String incomeing = (income / 100) + "";
			sp.edit().putString("jiubi", incomeing).commit();
			tradeprice = jsonObject.getDouble("realprice");
			String dealdatajson = jsonObject.getString("dealdata");
			jsonObject2 = new JSONObject(dealdatajson);
			trans = jsonObject2.getInt("buybacknum");
			tihuo = jsonObject2.getInt("consumenum");
			buygooding = jsonObject2.getInt("getnum");
			salgooding = jsonObject2.getInt("putnum");
			leftgoodassets = jsonObject2.getInt("stock");
			xia = jsonObject2.getString("downnum");
			// 认购
			rengou = jsonObject2.getInt("subnum");
			dealnum = jsonObject2.getInt("dealnum");
			totalbuy = jsonObject2.getDouble("buyintotal");
			totaloutmoney = jsonObject2.getDouble("buyouttotal");
			downaward = jsonObject2.getDouble("downaward");
			// 今日涨跌

			jsonArraylist = jsonObject.getJSONArray("todaydeal");

			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
			// Log.e("shunshun", tradeprice + "");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void UpdateUI() {
		df = new DecimalFormat("#0.00");

		realpri.setText(df.format((tradeprice / 100)));
		jiubi.setText(df.format((income / 100)));
		totalassets = leftgoodassets + buygooding;
		xiaji.setText(xia);
		total_assets.setText(totalassets + "");
		left_assets.setText(leftgoodassets + "");
		buying.setText(buygooding + "");
		saling.setText(salgooding + "");
		dealed.setText(dealnum + "");
		reward.setText(df.format((downaward / 100)));

		/*
		 * double shouyi = (tradeprice * (leftgoodassets + salgooding) +
		 * totaloutmoney - totalbuy) / 100;
		 */
		total_shouyi.setText(df
				.format((tradeprice * (leftgoodassets + salgooding)
						+ totaloutmoney - totalbuy) / 100));
		// 今日涨跌
		try {
			JSONObject object = (JSONObject) jsonArraylist.get(0);
			double firstprice = object.getInt("price");
			double today_zd = tradeprice - firstprice;
			total_zd.setText(df.format(today_zd / 100));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 交易曲线图适配器
	public class MypagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragmentsList1;

		public MypagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentsList1 = fragments;
		}

		public MypagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		private final String[] titles1 = { "实时", "每天" };

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles1[position];
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentsList1.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles1.length;
		}

	}

	// 买入卖出图标适配器
	public class MyBuySalepagerAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> fragmentsList2;

		public MyBuySalepagerAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.fragmentsList2 = fragments;
		}

		public MyBuySalepagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		private final String[] titles2 = { "卖出", "买入" };

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return titles2[position];
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentsList2.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles2.length;
		}

	}

	private static TradeServiceActivity instance;
	private ImageView product_ordsubmit_count_sub;
	private ImageView product_ordsubmit_count_add;
	private TextView product_ordsubmit_count;
	private EditText product_ordsubmit_price;
	private TextView product_total_price;
	private Button dialog_ok;
	private Button diaog_cancel;
	private int count_buy;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.tv_back:
			finish();
			break;

		case R.id.rb_buy_service:
			showBuyDialog();
			break;
		case R.id.rb_sale_service:
			showSaleDialog();
			break;

		case R.id.rb_tihuo_service:
			showTihuoDialog();
			break;

		case R.id.rb_zhuanrang_service:
			shouTransDialog();
			break;
		// 个人资产
		case R.id.person_assets:
			Intent intent = new Intent(TradeServiceActivity.this,
					TradeRecordActivity.class);
			startActivity(intent);

			break;

		default:
			break;
		}
	}

	int trans_count;

	// 转让
	private void shouTransDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View transView = (View) inflater.inflate(R.layout.trans_service, null);
		transTextView = (TextView) transView
				.findViewById(R.id.product_ordsubmit_count);
		transOk = (Button) transView.findViewById(R.id.dialog_ok);
		transCancel = (Button) transView.findViewById(R.id.dialog_cancel);
		transAdd = (ImageView) transView
				.findViewById(R.id.product_ordsubmit_count_add);
		transReduce = (ImageView) transView
				.findViewById(R.id.product_ordsubmit_count_sub);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(transView);
		builder.setCancelable(false);
		dialog1 = builder.show();
		transAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				trans_count++;
				transTextView.setText("" + trans_count);
			}
		});
		transReduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (trans_count > 1) {
					trans_count--;
					transTextView.setText("" + trans_count);
				} else {
					trans_count = 1;
				}

			}
		});
		transOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		transCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog1.dismiss();
				trans_count = 1;
			}
		});

	}

	// 提货
	int tihuo_count;

	private void showTihuoDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View tihuoView = (View) inflater.inflate(R.layout.tihuo_service, null);
		tihuoTextView = (TextView) tihuoView
				.findViewById(R.id.product_ordsubmit_count);
		tihuoAdd = (ImageView) tihuoView
				.findViewById(R.id.product_ordsubmit_count_add);
		tihuoreduce = (ImageView) tihuoView
				.findViewById(R.id.product_ordsubmit_count_sub);
		tihuoOk = (Button) tihuoView.findViewById(R.id.dialog_ok);

		tihuocancel = (Button) tihuoView.findViewById(R.id.dialog_cancel);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(tihuoView);
		builder.setCancelable(false);
		dialog0 = builder.show();

		tihuoAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tihuo_count++;
				tihuoTextView.setText("" + tihuo_count);
			}
		});
		tihuoreduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (tihuo_count > 1) {
					tihuo_count--;
					tihuoTextView.setText("" + tihuo_count);
				} else {
					tihuo_count = 1;
				}
			}
		});

		tihuoOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		tihuocancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog0.dismiss();
				tihuo_count = 1;
			}
		});

	}

	int sale_count = 1;

	// 卖出按钮
	private void showSaleDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View saleView = (View) inflater.inflate(R.layout.sale_service, null);
		product_ordsubmit_count2 = (TextView) saleView
				.findViewById(R.id.product_ordsubmit_count);
		// 卖出价
		salePrice = (EditText) saleView
				.findViewById(R.id.product_ordsubmit_price);
		// 加号
		add = (ImageView) saleView
				.findViewById(R.id.product_ordsubmit_count_add);
		// 减号
		reduce = (ImageView) saleView
				.findViewById(R.id.product_ordsubmit_count_sub);
		ok = (Button) saleView.findViewById(R.id.dialog_ok);
		cancel = (Button) saleView.findViewById(R.id.dialog_cancel);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(saleView);
		builder.setCancelable(false);
		dialog = builder.show();
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sale_count++;
				product_ordsubmit_count2.setText("" + sale_count);

			}
		});
		reduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (sale_count > 1) {
					sale_count--;
					product_ordsubmit_count2.setText("" + sale_count);
				} else {
					sale_count = 1;
				}

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				sale_count = 1;
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

	}

	// 买入按钮
	private void showBuyDialog() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = LayoutInflater.from(this);
		View view = (View) inflater.inflate(R.layout.buy_service, null);
		// 增加
		product_ordsubmit_count_sub = (ImageView) view
				.findViewById(R.id.product_ordsubmit_count_sub);
		// 减少
		product_ordsubmit_count_add = (ImageView) view
				.findViewById(R.id.product_ordsubmit_count_add);
		// 数量
		product_ordsubmit_count = (TextView) view
				.findViewById(R.id.product_ordsubmit_count);

		// 买入价格
		product_ordsubmit_price = (EditText) view
				.findViewById(R.id.product_ordsubmit_price);
		product_ordsubmit_price.addTextChangedListener(textWatcher);

		// 总价钱
		product_total_price = (TextView) view
				.findViewById(R.id.product_total_price);

		dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
		diaog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
		final AlertDialog builder = new AlertDialog.Builder(this).create();
		builder.setView(view, 0, 0, 0, 0);
		builder.setCancelable(false);
		builder.show();

		// 取消按钮
		diaog_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				builder.dismiss();
				count_buy = 1;
			}
		});
		// 确定按钮
		dialog_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		product_ordsubmit_count_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				count_buy++;
				product_ordsubmit_count.addTextChangedListener(textWatcher);
				product_ordsubmit_count.setText(count_buy + "");

			}

		});
		product_ordsubmit_count_sub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (count_buy > 1) {

					count_buy--;
					product_ordsubmit_count.addTextChangedListener(textWatcher);

					product_ordsubmit_count.setText(count_buy + "");

				} else {
					count_buy = 1;
				}

			}
		});

	}

	// 买入价监听
	private TextWatcher textWatcher = new TextWatcher() {
		private CharSequence charSequence;
		private String price;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

			price = product_ordsubmit_price.getText().toString().trim();
			if (!TextUtils.isEmpty(price)) {

				product_total_price.setText(""
						+ Arith.mul(Double.parseDouble(price), count_buy));
			} else {

				product_total_price.setText("0.00");
			}

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

	private Dialog dialog2;
	private EditText salePrice;
	private ImageView add;
	private ImageView reduce;
	private Button ok;
	private Button cancel;
	private Dialog dialog;
	private TextView product_ordsubmit_count2;
	private TextView tihuoTextView;
	private ImageView tihuoAdd;
	private ImageView tihuoreduce;
	private Button tihuoOk;
	private Button tihuocancel;
	private Dialog dialog0;
	private TextView transTextView;
	private Button transOk;
	private Button transCancel;
	private Dialog dialog1;
	private ImageView transAdd;
	private ImageView transReduce;
	private String unionid;
	private String dgid;
	private double income;
	private double tradeprice;
	private int trans;
	private int tihuo;
	private int buygooding;
	private int salgooding;
	private int leftgoodassets;
	private int rengou;
	private int dealnum;
	private double totalbuy;
	private double totaloutmoney;
	private double downaward;

	private int totalassets;

	private JSONObject jsonObject2;

	private JSONArray jsonArraylist;

	private DecimalFormat df;

	private String xia;

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopThread = false;
		super.onDestroy();
		handler.removeMessages(1);
		handler.removeMessages(2);
	}
}
