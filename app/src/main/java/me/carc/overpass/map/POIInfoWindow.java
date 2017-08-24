package me.carc.overpass.map;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import me.carc.overpass.R;
import me.carc.overpasslib.overpass.OverpassQueryResult;

/**
 * A customized InfoWindow handling POIs. 
 * We inherit from MarkerInfoWindow as it already provides most of what we want. 
 * And we just add support for a "more info" button. 
 * 
 * @author M.Kergall
 */
public class POIInfoWindow extends MarkerInfoWindow {
	
	private OverpassQueryResult.Element mSelectedPOI;
	
	public POIInfoWindow(MapView mapView) {
		super(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, mapView);
		
		Button btn = (Button)(mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_moreinfo));
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (mSelectedPOI.tags.website != null){
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSelectedPOI.tags.website));
					view.getContext().startActivity(myIntent);
				}
			}
		});
	}

	@Override public void onOpen(Object item){
		Marker marker = (Marker)item;
		mSelectedPOI = (OverpassQueryResult.Element)marker.getRelatedObject();
		super.onOpen(item);
		
		//Fetch the thumbnail in background

		if (mSelectedPOI.tags.image != null){
			ImageView imageView = (ImageView)mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_image);
			imageView.setImageResource(R.drawable.person);
			imageView.setVisibility(View.VISIBLE);
		}


		//Show or hide "more info" button:
		if (mSelectedPOI.tags.website != null)
			mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_moreinfo).setVisibility(View.VISIBLE);
		else
			mView.findViewById(org.osmdroid.bonuspack.R.id.bubble_moreinfo).setVisibility(View.GONE);
		
	}
}
