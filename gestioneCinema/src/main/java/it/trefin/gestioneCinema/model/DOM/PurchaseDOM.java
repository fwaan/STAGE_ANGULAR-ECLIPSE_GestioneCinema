package it.trefin.gestioneCinema.model.DOM;


import it.trefin.gestioneCinema.model.enums.PaymentMethodE;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;

public class PurchaseDOM{
	
	private ProjectionKey prj;
	private long customer_id;
	private boolean omage = false;
	private boolean fast_track = false;
	private PaymentMethodE pm;
	
	
	
	public PurchaseDOM(ProjectionKey prj, long customer_id, PaymentMethodE pm) {
		super();
		this.prj = prj;
		this.customer_id = customer_id;
		this.pm = pm;
	}
	public PurchaseDOM() {
		super();
	}
	public PurchaseDOM(ProjectionKey prj, long customer_id, boolean omage, boolean fast_track, PaymentMethodE pm) {
		super();
		this.prj = prj;
		this.customer_id = customer_id;
		this.omage = omage;
		this.fast_track = fast_track;
		this.pm = pm;
	}
	public ProjectionKey getPrj() {
		return prj;
	}
	public void setPrj(ProjectionKey prj) {
		this.prj = prj;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public boolean isOmage() {
		return omage;
	}
	public void setOmage(boolean omage) {
		this.omage = omage;
	}
	public boolean isFast_track() {
		return fast_track;
	}
	public void setFast_track(boolean fast_track) {
		this.fast_track = fast_track;
	}
	public PaymentMethodE getPm() {
		return pm;
	}
	public void setPm(PaymentMethodE pm) {
		this.pm = pm;
	}


}
