package org.droidplanner.android.gcs.follow;

import org.droidplanner.core.drone.Drone;
import org.droidplanner.core.helpers.coordinates.Coord2D;
import org.droidplanner.core.helpers.geoTools.GeoTools;
import org.droidplanner.core.helpers.math.MathUtil;
import org.droidplanner.core.helpers.units.Length;

import android.location.Location;

public class FollowCircle extends FollowAlgorithm {

	/**
	 * °/s
	 */
	private double circleStep = 20;
	private double circleAngle = 0.0;

	public FollowCircle(Drone drone, Length radius, double rate) {
		super(drone, radius);
		circleStep = rate;
	}

	@Override
	public FollowModes getType() {
		return FollowModes.CIRCLE;
	}

	@Override
	public void processNewLocation(Location location) {
		Coord2D gcsCoord = new Coord2D(location.getLatitude(),
				location.getLongitude());
		Coord2D goCoord = GeoTools.newCoordFromBearingAndDistance(gcsCoord,
				circleAngle, radius.valueInMeters());
		circleAngle = MathUtil.constrainAngle(circleAngle + circleStep);
		drone.guidedPoint.newGuidedCoord(goCoord);
	}
}
