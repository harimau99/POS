package com.iii.pos.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.iii.pos.R;

public class Header_Pos extends Fragment {
	// -----------------constant-----------------------------//
	final static String ARG_POSITION = "position";

	// -----------------the fields---------------------------//
	private int mCurrentPosition = -1;

	// action id for menu custom
	private static final int ID_LANGUAGE = 1;
	private static final int ID_CURRENCY = 2;
	private static final int ID_SERVER = 3;
	private static final int ID_INFO = 4;

	private QuickMenuAction quickAction;
	// ---------callback for action--------------
	OnHeadderSelectedListener mCallback;

	// The container Activity must implement this interface so the frag can
	// deliver messages
	public interface OnHeadderSelectedListener {
		/** Called by HeadlinesFragment when a list item is selected */
		public void onMenuButtonClick(int btnKey);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View headerLayout = inflater.inflate(R.layout.header_pos,
				container, false);
		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {
			mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
		}
		ActionMenuItem languageItem = new ActionMenuItem(ID_LANGUAGE,
				"Language", getResources().getDrawable(
						R.drawable.menu_down_arrow));
		ActionMenuItem currencyItem = new ActionMenuItem(ID_CURRENCY,
				"Currency", getResources()
						.getDrawable(R.drawable.menu_up_arrow));
		ActionMenuItem serverItem = new ActionMenuItem(ID_SERVER, "SERVER",
				getResources().getDrawable(R.drawable.menu_search));
		ActionMenuItem infoItem = new ActionMenuItem(ID_INFO, "INFO",
				getResources().getDrawable(R.drawable.menu_info));

		// use setSticky(true) to disable QuickAction dialog being dismissed
		// after an item is clicked
		currencyItem.setSticky(true);
		languageItem.setSticky(true);

		// create QuickAction. Use QuickAction.VERTICAL or
		// QuickAction.HORIZONTAL param to define layout
		// orientation
		quickAction = new QuickMenuAction(headerLayout.getContext(),
				QuickMenuAction.VERTICAL);

		// add action items into QuickAction
		quickAction.addActionItem(languageItem);
		quickAction.addActionItem(currencyItem);
		quickAction.addActionItem(serverItem);
		quickAction.addActionItem(infoItem);

		// Set listener for action item clicked
		quickAction
				.setOnActionItemClickListener(new QuickMenuAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickMenuAction source, int pos,
							int actionId) {
						ActionMenuItem actionItem = quickAction
								.getActionItem(pos);

						// here we can filter which action item was clicked with
						// pos or actionId parameter
						if (actionId == ID_LANGUAGE) {
							Toast.makeText(headerLayout.getContext(),
									"Let's do some search action",
									Toast.LENGTH_SHORT).show();
						} else if (actionId == ID_INFO) {
							Toast.makeText(headerLayout.getContext(),
									"I have no info this time",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(headerLayout.getContext(),
									actionItem.getTitle() + " selected",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		// set listnener for on dismiss event, this listener will be called only
		// if QuickAction dialog was dismissed
		// by clicking the area outside the dialog.
		quickAction
				.setOnDismissListener(new QuickMenuAction.OnDismissListener() {
					@Override
					public void onDismiss() {
						Toast.makeText(headerLayout.getContext(), "Dismissed",
								Toast.LENGTH_SHORT).show();
					}
				});

		// show on btnSetting
		Button btnSetting = (Button) headerLayout.findViewById(R.id.btnSetting);
		btnSetting.setOnClickListener(btnFragmentOnClickListener);
		// show on btnSetting
		Button btnTableMap = (Button) headerLayout.findViewById(R.id.btnMap);
		btnTableMap.setOnClickListener(btnFragmentOnClickListener);
		// show on btnSetting
		Button btnInvoice = (Button) headerLayout.findViewById(R.id.btnInvoice);
		btnInvoice.setOnClickListener(btnFragmentOnClickListener);
		// show on btnSetting
		Button btnLogin_out = (Button) headerLayout
				.findViewById(R.id.btnLogin_Out);
		btnLogin_out.setOnClickListener(btnFragmentOnClickListener);
		// Inflate the layout for this fragment
		return headerLayout;
	}

	Button.OnClickListener btnFragmentOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int btnKey = v.getId();
			if (quickAction != null) {
				if (R.id.btnSetting == btnKey) {
					quickAction.show(v);
				}
			}
			switch (btnKey) {
			case R.id.btnMap:
				btnKey = 1;
				break;
			case R.id.btnInvoice:
				btnKey = 2;
				break;
			case R.id.btnSetting:
				btnKey = 3;
				break;
			case R.id.btnLogin_Out:
				btnKey = 4;
				break;

			default:
				break;
			}
			mCallback.onMenuButtonClick(btnKey);
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception.
		try {
			mCallback = (OnHeadderSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnHeadlineSelectedListener");
		}
	}
}