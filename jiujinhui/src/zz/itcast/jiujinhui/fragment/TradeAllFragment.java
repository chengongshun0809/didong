package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnScroll;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.activity.TiXianRecordActivity.MyAdapter;
import zz.itcast.jiujinhui.res.NetUtils;
import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TradeAllFragment extends BaseFragment {
	@ViewInject(R.id.cominglistview)
	private ListView listview;

	@ViewInject(R.id.tv__title)
	private TextView tv__title;
	@ViewInject(R.id.tv_back)
	private ImageView tv_back;
	@ViewInject(R.id.Rl_jindu)
	private RelativeLayout Rl_jindu;
	boolean stopThread = false;
	private SharedPreferences sp;
	private ListViewAdapter adapter;
	private String unionIDString;
	  
	private int lastindex = 0;// 最后的可视项索引
	
	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				/*
				 * data.clear(); data.addAll(incomeslist);
				 * Rl_jindu.setVisibility(View.GONE); adapter = new MyAdapter();
				 * cominglistview.setAdapter(adapter);
				 * adapter.notifyDataSetChanged();
				 */
				/*
				 * data.clear(); data.addAll(orderlist);
				 */
				Rl_jindu.setVisibility(View.GONE);

				// listview.setAdapter(adapter);
				listview.addFooterView(loadmoreview);
				adapter = new ListViewAdapter(mlist);
				count = adapter.getCount();
				for (int i = count; i < count + 10; i++) {
					List<Map<String, Object>> mlists = new ArrayList<Map<String, Object>>();
					mlists.add(orderlist.get(i));
					adapter.addLists(mlists);
				}

				listview.setAdapter(adapter);

				adapter.notifyDataSetChanged();
				
				break;
			case 2:

				break;
			default:
				break;
			}

		};
	};
	private ArrayList<Map<String, Object>> data = null;

	private List<Map<String, Object>> mlist = new ArrayList<Map<String, Object>>();

	private Button loadmoreButton;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {
					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/mydeallist?unionid="
							+ unionIDString;

					try {
						HttpsURLConnection connection = NetUtils
								.httpsconnNoparm(url_serviceinfo, "POST");

						int code = connection.getResponseCode();
						if (code == 200) {
							iStream = connection.getInputStream();
							String infojson = NetUtils.readString(iStream);
							JSONObject jsonObject = new JSONObject(infojson);
							// Log.e("我靠快快快快快快快", jsonObject.toString());
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

	private ArrayList<Map<String, Object>> orderlist;

	// 解析数据
	protected void parseJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> map;
			orderlist = new ArrayList<Map<String, Object>>();

			JSONArray jsonlist = jsonObject.getJSONArray("orders");
			for (int i = 0; i < jsonlist.length(); i++) {
				JSONObject jObject = (JSONObject) jsonlist.get(i);
				String danhao = jObject.getString("oid");
				String type = jObject.getString("type");
				String name = jObject.getString("pname");
				// 根据是否成功完成来判断状态
				String undonenum = jObject.getString("undonenum");
				String date = jObject.getString("createtime");
				DecimalFormat df = new DecimalFormat("#0.00");
				double total = jObject.getDouble("total");
				String number_total = jObject.getString("num");

				map = new HashMap<String, Object>();
				map.put("danhao", danhao);
				map.put("type", type);
				map.put("name", name);
				map.put("undonenum", undonenum);
				map.put("date", date);
				map.put("total", df.format(total / 100));
				map.put("number_total", number_total);
				orderlist.add(map);

			}
			Message message = handler.obtainMessage();
			message.what = 1;
			handler.sendMessage(message);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	LayoutInflater inflater;

	private int count;

	public class ListViewAdapter extends BaseAdapter {

		private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		public ListViewAdapter(List<Map<String, Object>> list) {
			super();
			this.list = list;
			this.list.clear();
			this.list.addAll(list);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.trade_record_detail,
						null);
				holder.tv_danhao = (TextView) convertView
						.findViewById(R.id.danhao);
				holder.tv_dan_state = (TextView) convertView
						.findViewById(R.id.dan_state);
				holder.name_pro = (TextView) convertView
						.findViewById(R.id.name_pro);
				holder.msg_chengjiao = (TextView) convertView
						.findViewById(R.id.msg_chengjiao);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.tv_num = (TextView) convertView.findViewById(R.id.tvnum);
				holder.total = (TextView) convertView.findViewById(R.id.total);
				holder.tv_weichengjiao = (TextView) convertView
						.findViewById(R.id.tv_weichengjiao);
				holder.tv_weichengjiao_num = (TextView) convertView
						.findViewById(R.id.tv_weichengjiao_num);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// holder.tv.setText(list.get(position));

			holder.tv_danhao.setText(list.get(position).get("danhao") + "");
			holder.name_pro.setText(list.get(position).get("name") + "");
			holder.date.setText(list.get(position).get("date") + "");
			holder.total.setText(list.get(position).get("total") + "");
			holder.tv_num.setText(list.get(position).get("number_total") + "");
			String typString = (String) list.get(position).get("type");
			// 未成交
			String undonenum = (String) list.get(position).get("undonenum");
			double typedouble = Double.parseDouble(typString);
			/*
			 * if (typedouble == 9 || typedouble == 3 || typedouble == 1 ||
			 * typedouble == 2) { holder.msg_chengjiao.setText("全部成交"); }
			 */

			// 判断type
			int type_int = Integer.parseInt(typString);
			switch (type_int) {
			case 9:
				holder.tv_dan_state.setText("已付款");
				holder.msg_chengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao_num.setVisibility(View.GONE);
				holder.msg_chengjiao.setText("全部成交");

				break;
			case 3:
				holder.tv_dan_state.setText("卖出完成");
				holder.msg_chengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao_num.setVisibility(View.GONE);
				holder.msg_chengjiao.setText("全部成交");

				break;
			case 1:
				holder.tv_dan_state.setText("认购完成");
				holder.msg_chengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao_num.setVisibility(View.GONE);
				holder.msg_chengjiao.setText("全部成交");
				break;
			case 6:
				holder.tv_dan_state.setText("提货撤回");
				holder.msg_chengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao_num.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao_num.setText(undonenum);
				break;
			case 2:
				holder.tv_dan_state.setText("买入完成");
				holder.msg_chengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao_num.setVisibility(View.GONE);
				holder.msg_chengjiao.setText("全部成交");
				break;
			case 5:
				holder.tv_dan_state.setText("卖出撤回");

				holder.msg_chengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao_num.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao_num.setText(undonenum);

				break;

			default:
				break;
			}

			return convertView;
		}

		public class ViewHolder {
			TextView tv_danhao;
			TextView tv_dan_state;
			TextView name_pro;
			TextView msg_chengjiao;
			TextView date;
			TextView total;
			TextView tv_num;
			TextView tv_weichengjiao;
			TextView tv_weichengjiao_num;
		}

		public void addLists(List<Map<String, Object>> mlist) {
			list.addAll(mlist);
		}

	}
	private int itemcount;// 当前窗口可见项总数
	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		loadmoreButton.setOnClickListener(this);

		
		listview.setOnScrollListener(new OnScrollListener() {

			public  int itemcount;// 当前窗口可见项总数

			@Override
			public void onScroll(AbsListView view,
					int firstitem, int itemcount,
					int thirditem) {
				// TODO Auto-generated method stub
				    this.itemcount = itemcount;
			        firstitem = lastindex + itemcount - 1;
			}

			@Override
			public void onScrollStateChanged(AbsListView view,
					int srcollitem) {
				// TODO Auto-generated method stub
				 int lastindex = adapter.getCount() - 1;
			        int lastIndex = lastindex + 1;
			        if (srcollitem == OnScrollListener.SCROLL_STATE_IDLE
			                && lastindex == lastIndex) {
			            Log.i("LoadMore", "loading...");
			        	listview.setSelection(lastindex - itemcount + 1);//
			        }
			}

		});
	}

	private View loadmoreview;

	@Override
	public void initView(View view) {
		// TODO Afuto-generated method stub
		ViewUtils.inject(this, view);
		sp = getActivity().getSharedPreferences("user", 0);
		unionIDString = sp.getString("unionid", null);
		Rl_jindu.setVisibility(View.VISIBLE);
		loadmoreview = LayoutInflater.from(getActivity()).inflate(
				R.layout.load_more, null);
		data = new ArrayList<Map<String, Object>>();
		inflater = getActivity().getLayoutInflater();
		loadmoreButton = (Button) loadmoreview.findViewById(R.id.load);

	}

	// 点击按钮事件
	Handler mHandler = new Handler();

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.load:
			loadmoreButton.setText("正在加载...");
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if ((adapter.getCount() + 1) > orderlist.size()) {// 此处判断list集合里的数据是否加载完全

						loadmoreButton.setText("暂无数据");
						loadmoreButton.setEnabled(false);
					} else {
						// TODO Auto-generated method stub

						loadData();
						adapter.notifyDataSetChanged();
					
						// 设置选中项

						loadmoreButton.setText("加载更多");
					}

				}
			}, 2000);

			break;

		default:
			break;
		}
	}

	protected void loadData() {
		count = adapter.getCount();
		for (int i = count; i < count
				+ ((orderlist.size() - count >= 10) ? 10 : orderlist.size()
						- count); i++) {
			List<Map<String, Object>> mlists = new ArrayList<Map<String, Object>>();
			mlists.add(orderlist.get(i));
			adapter.addLists(mlists);
		}

	}

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.tradeall_fragment;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

		stopThread = true;
		super.onDestroy();

	}
}
