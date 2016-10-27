/**计算出中奖 云码**/
	public long toClacLotteryCode(Product _cloudProduct) {
		if(_cloudProduct.getBuyStatus()!=2 || _cloudProduct.getEndTime()==null ||_cloudProduct.getPRemainedNums()>0){
			return 0L;
		}
		int hasNums=_cloudProduct.getPNums();//商品需要份数的余数
		
		TCloudUser tmpCloudUser=new TCloudUser();
		tmpCloudUser.setCreateTime(_cloudProduct.getEndTime());
		List<TCloudUser> timeLists=tPrizeCloudUserService.searchTPrizeCloudUserLast100(tmpCloudUser);
		BigDecimal sumTime=BigDecimal.ZERO;
		for(TCloudUser _tmpCodeUser:timeLists){
			String timeStr=DateUtil.dateToString("HH:mm:ss:sss", _tmpCodeUser.getCreateTime());
			//System.out.println(timeStr.replaceAll(":", ""));  
			sumTime=sumTime.add(new BigDecimal(timeStr.replaceAll(":", "")));
		}
		//System.out.println("sumTime=="+sumTime+"===hasNums=="+hasNums+"===="+(sumTime.longValue() % hasNums));
		//计算结果=截止商品最后兑换时间，平台最后100条交易记录时间之和  除以商品需要份数的余数+10000001=幸运号码（如结果有小数，请四舍五入）
		long _lotteryCode= (sumTime.longValue() % hasNums)+ new Long(10000001);
		log.debug("---------_lotteryCode------------"+_lotteryCode);
		return _lotteryCode;
	}
