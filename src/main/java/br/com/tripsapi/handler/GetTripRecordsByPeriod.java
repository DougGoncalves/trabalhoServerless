package br.com.tripsapi.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.tripsapi.dao.TripRepository;
import br.com.tripsapi.model.HandlerRequest;
import br.com.tripsapi.model.HandlerResponse;
import br.com.tripsapi.model.Trip;

public class GetTripRecordsByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

	private final TripRepository repository = new TripRepository();
	
	@Override
	public HandlerResponse handleRequest(HandlerRequest request, Context context) {

		final String starts = request.getQueryStringParameters().get("starts");
		final String ends = request.getQueryStringParameters().get("ends");

		context.getLogger().log("Searching for registered trips between " + starts + " and " + ends);

		final List<Trip> trips = this.repository.findByPeriod(starts, ends);
		
		if(trips == null || trips.isEmpty()) {
			return HandlerResponse.builder().setStatusCode(404).build();
		}
		
		return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
	}
}	