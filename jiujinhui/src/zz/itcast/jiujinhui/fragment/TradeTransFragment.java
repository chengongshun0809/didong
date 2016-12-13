package zz.itcast.jiujinhui.fragment;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.jiujinhui.R;
import zz.itcast.jiujinhui.res.NetUtils;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TradeTransFragment extends BaseFragment {
	@ViewInject(R.id.colistview)
	private ListView listview;
	@ViewInject(R.id.tv_null)
	private RelativeLayout tv_null;

	@ViewInject(R.id.Rl_jindu)
	private RelativeLayout Rl_jindu;
	boolean stopThread = false;
	private SharedPreferences sp;
	private ListViewAdapter adapter;
	private String unionIDString;

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				data.clear();
				data.addAll(orderlist);
				Rl_jindu.setVisibility(View.GONE);
				adapter = new ListViewAdapter();
				listview.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;
			case 2:
				Rl_jindu.setVisibility(View.GONE);
				listview.setVisibility(View.GONE);
				tv_null.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

		};
	};
	private ArrayList<Map<String, Object>> data = null;

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			private InputStream iStream;

			@Override
			public void run() {
				while (!stopThread) {
					String url_serviceinfo = "https://www.4001149114.com/NLJJ/ddapp/mydeallist?unionid="
							+ unionIDString + "&type=7";
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

							stopThread=true;
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
			if (jsonlist.length() > 0) {
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

			} else {
				handler.sendEmptyMessage(2);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	LayoutInflater inflater;

	public class ListViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
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

			holder.tv_danhao.setText(data.get(position).get("danhao") + "");
			holder.name_pro.setText(data.get(position).get("name") + "");
			holder.date.setText(data.get(position).get("date") + "");
			holder.total.setText(data.get(position).get("total") + "");
			holder.tv_num.setText(data.get(position).get("number_total") + "");
			String typString = (String) data.get(position).get("type");
			// 未成交
			String undonenum = (String) data.get(position).get("undonenum");
			// 判断type
			int type_int = Integer.parseInt(typString);
			switch (type_int) {
			case 7:
				holder.tv_dan_state.setText("转让完成");
				holder.msg_chengjiao.setVisibility(View.VISIBLE);
				holder.tv_weichengjiao.setVisibility(View.GONE);
				holder.tv_weichengjiao_num.setVisibility(View.GONE);
				holder.msg_chengjiao.setText("全部成交");
				

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

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initView(View view) {
		// TODO Afuto-generated method stub
		ViewUtils.inject(this, view);
		sp = getActivity().getSharedPreferences("user", 0);
		unionIDString = sp.getString("unionid", null);
		Rl_jindu.setVisibility(View.VISIBLE);

		data = new ArrayList<Map<String, Object>>();
		inflater = getActivity().getLayoutInflater();

	}

	// 点击按钮事件

	@Override
	public int getLayoutResID() {
		// TODO Auto-generated method stub
		return R.layout.tradetrans_fragment;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		stopThread=false;
		data.clear();
		handler.removeMessages(2);
        handler.removeMessages(1);
	}

}
