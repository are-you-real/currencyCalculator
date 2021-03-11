package currencyCalculator_GBP_PLN;

public class currencyConverter {
	private Double rate;
	
	public currencyConverter() {
		webAPI wApi = new webAPI();
		rate = wApi.getRate();
	}
	
	public Double anotherCurrency2pln(Double anotherCurrency) {
		return anotherCurrency*rate;
	}
	
	public Double pln2anotherCurrency(Double pln) {
		return pln/rate;
	}
}
