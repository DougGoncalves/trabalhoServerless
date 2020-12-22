package br.com.tripsapi.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.tripsapi.dao.TripRepository;
import br.com.tripsapi.model.HandlerRequest;
import br.com.tripsapi.model.HandlerResponse;
import br.com.tripsapi.model.Trip;

public class GetTripRecordsByCity implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();

	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String country = request.getPathParameters().get("country");
		final String city = request.getQueryStringParameters().get("city");

		context.getLogger().log("Searching for registered trips for country " + country + " and city equals " + city);

		final List<Trip> trips = this.repository.findByCity(country, city);

		if (trips == null || trips.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}

		return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
	}
}