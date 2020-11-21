package com.example.espritindoor.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.espritindoor.Model.VenueSelectorItem;
import com.example.espritindoor.R;
import com.example.espritindoor.ViewModel.IndoorFragment;
import com.example.espritindoor.listeners.IVenueClickedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jose J Varó (jjv@mapspeople.com) on 13-04-2017.
 */

public class VenueSelectorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private static final String TAG = VenueSelectorAdapter.class.getSimpleName();

	/* ======================================================================================
		List item types (in this case, one). Will be used on the vertical direction panel
	 */
	private static final int TYPE_VENUE_ITEM    = 0;



	private IVenueClickedListener mClickListener;
	private List<VenueSelectorItem> mItemList;
	IndoorFragment fragementExplore = new IndoorFragment();
	Context mContext ;
	private String userId;
private  String username;

	public VenueSelectorAdapter(IVenueClickedListener listener , Context context ,String id ,String username)
	{
		mClickListener = listener;
		mItemList = new ArrayList<>();
		this.mContext = context;
		setItems( null );
		this.userId = id ;
		this.username = username;
		System.out.println("mil adapteur :"+this.userId);
		System.out.println("mil adapteur :"+this.username);
	}


	////////////////////////////////////////

	private ItemClickListene mlistene ;

	public  interface ItemClickListene{
		void launchIndoor(int position);
	}

	public void setonItemClick ( ItemClickListene listener)
	{
		mlistene = listener;
	}
	////////////////////////////////////////



	//region RecyclerView.Adapter
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {

		switch( viewType )
		{
			case TYPE_VENUE_ITEM:
				View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.control_venue_selector_item, parent, false );
				return new VenueViewHolder(LayoutInflater.from( parent.getContext() ).inflate( R.layout.control_venue_selector_item, parent, false ),mlistene );

				//new ListFriendHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_friend,parent,false),mlistene) ;
		}

		return null;
	}

	@Override
	public void onBindViewHolder( RecyclerView.ViewHolder holder, int position ) {
			onBindVenueViewHolder( (VenueViewHolder)holder, position );
	}

	@Override
	public int getItemViewType( int position ) {

		// return a venue type for any position
		return TYPE_VENUE_ITEM;
	}

	@Override
	public int getItemCount() {
		return mItemList.size();
	}
	//endregion


	public void setItems( List< VenueSelectorItem > items )
	{
		mItemList.clear();

		if( items != null ) {

			mItemList.addAll( items );
		}

		notifyDataSetChanged();
	}

	private void onBindVenueViewHolder( VenueViewHolder holder, int position )
	{
		VenueSelectorItem item = mItemList.get( position );

		holder.mVenueName.setText( item.getRenderName() );

		Bitmap bmp = item.getImageBmp();
		holder.mVenueImage.setImageBitmap( bmp );
		/*holder.mVenueImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Bundle bundle = new Bundle();
				bundle.putString("key",holder.mVenueName.getText().toString());
				bundle.putString("key1",userId);// Put anything what you want
				bundle.putString("key2",username);
				fragementExplore.setArguments(bundle);

				((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
						.replace(R.id.flContent, fragementExplore).addToBackStack(null).commit();
			}
		});*/
	}













	//region View Holders
	private class VenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		private ImageView mVenueImage;
		private TextView mVenueName;

		VenueViewHolder( View itemView , final ItemClickListene listener) {
			super( itemView );

			mVenueImage = itemView.findViewById( R.id.venue_selector_item_img );
			mVenueName = itemView.findViewById( R.id.venue_selector_item_text );


			mVenueImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(listener!= null)
					{
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION)
						{
							listener.launchIndoor(position);
						}

					}
				}
			});

			// set the click listener to the above item's parent
			itemView.setOnClickListener( this );
		}

		@Override
		public void onClick( View v ) {
			if( mClickListener != null ) {
				VenueSelectorItem clickedItem = mItemList.get( getLayoutPosition() );

				mClickListener.OnVenueClicked( clickedItem.getId() );
			}
		}
	}
	//endregion
}
