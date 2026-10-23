package com.vsr.composite;

import java.sql.Date;

public class Product {
	
	
		private Long productId;
		
		private String productName;
		private String productDescription;
		private Long rate;
		private Date makeDate;
		private Date expeirDate;
		private String activeFlag;
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getProductDescription() {
			return productDescription;
		}
		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}
		public Long getRate() {
			return rate;
		}
		public void setRate(Long rate) {
			this.rate = rate;
		}
		public Date getMakeDate() {
			return makeDate;
		}
		public void setMakeDate(Date makeDate) {
			this.makeDate = makeDate;
		}
		public Date getExpeirDate() {
			return expeirDate;
		}
		public void setExpeirDate(Date expeirDate) {
			this.expeirDate = expeirDate;
		}
		public String getActiveFlag() {
			return activeFlag;
		}
		public void setActiveFlag(String activeFlag) {
			this.activeFlag = activeFlag;
		}
}
