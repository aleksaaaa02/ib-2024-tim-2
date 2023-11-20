package rs.ac.uns.ftn.Bookify.model;

import java.util.List;

public class Accommodation {

	private String name;
	private String description;
	private int minGuest;
	private int maxGuest;
	private int cancellationDeadline;
	private boolean accepted;
	private boolean manual = true;
	private List<PricelistItem> priceList;
	private List<Availability> availability;
	private List<Rating> ratings;
	private List<Filter> filters;
	private AccommodationType accommodationType;
	private PricePer pricePer;
	private Address address;

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return this.address;
	}

	public void setFilter(List<Filter> filters){
		this.filters = filters;
	}

	public List<Filter> getFilters(){
		return this.filters;
	}

	public void setAccommodationType(AccommodationType accommodationType){
		this.accommodationType = accommodationType;
	}

	public AccommodationType getAccommodationType(){
		return this.accommodationType;
	}

	public void setPricePer(PricePer pricePer){
		this.pricePer = pricePer;
	}

	public PricePer getPricePer(){
		return this.pricePer;
	}	
	public void setRatings(List<Rating> ratings){
		this.ratings = ratings;
	}

	public List<Rating> getRatings(){
		return this.ratings;
	}

	public void setPriceList(List<PricelistItem> priceList){
		this.priceList = priceList;
	}

	public List<PricelistItem> getPriceList(){
		return this.priceList;
	}

	public List<Availability> getAvailability(){
		return this.availability;
	}

	public void setAvailability(List<Availability> availability){
		this.availability = availability;
	}

	public String getName() {
 		return this.name;
	}

	public void setName(String name) {
 		this.name = name;
	}

	public String getDescription() {
 		return this.description; 
	}

	public void setDescription(String description) {
 		this.description = description; 
	}

	public int getMinGuest() {
 		return this.minGuest; 
	}

	public void setMinGuest(int minGuest) {
 		this.minGuest = minGuest; 
	}

	public int getMaxGuest() {
 		return this.maxGuest; 
	}

	public void setMaxGuest(int maxGuest) {
 		this.maxGuest = maxGuest; 
	}

	public int getCancellationDeadline() {
 		return this.cancellationDeadline; 
	}

	public void setCancellationDeadline(int cancellationDeadline) {
 		this.cancellationDeadline = cancellationDeadline; 
	}

	public boolean getAccepted() {
 		return this.accepted; 
	}

	public void setAccepted(boolean accepted) {
 		this.accepted = accepted; 
	}

	public boolean getManual() {
 		return this.manual; 
	}

	public void setManual(boolean manual) {
 		this.manual = manual; 
	}

}
