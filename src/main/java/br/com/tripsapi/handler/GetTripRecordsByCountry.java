package br.com.tripsapi.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.tripsapi.dao.TripRepository;
import br.com.tripsapi.model.HandlerRequest;
import br.com.tripsapi.model.HandlerResponse;
import br.com.tripsapi.model.Trip;

public class GetTripRecordsByCountry implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		context.getLogger().log("Searching for registered trips for ");

		final String country = request.getPathParameters().get("country");

		context.getLogger().log("Searching for registered trips for " + country);

		final List<Trip> trips = this.repository.findByCountry(country);

		if (trips == null || trips.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}
		context.getLogger().log(trips.toString());
		return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
	}
}