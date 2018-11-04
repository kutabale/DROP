
package org.drip.analytics.daycount;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
 * Copyright (C) 2014 Lakshmi Krishnamurthy
 * Copyright (C) 2013 Lakshmi Krishnamurthy
 * Copyright (C) 2012 Lakshmi Krishnamurthy
 * 
 *  This file is part of DROP, an open-source library targeting risk, transaction costs, exposure, margin
 *  	calculations, and portfolio construction within and across fixed income, credit, commodity, equity,
 *  	FX, and structured products.
 *  
 *  	https://lakshmidrip.github.io/DROP/
 *  
 *  DROP is composed of three main modules:
 *  
 *  - DROP Analytics Core - https://lakshmidrip.github.io/DROP-Analytics-Core/
 *  - DROP Portfolio Core - https://lakshmidrip.github.io/DROP-Portfolio-Core/
 *  - DROP Numerical Core - https://lakshmidrip.github.io/DROP-Numerical-Core/
 * 
 * 	DROP Analytics Core implements libraries for the following:
 * 	- Fixed Income Analytics
 * 	- Asset Backed Analytics
 * 	- XVA Analytics
 * 	- Exposure and Margin Analytics
 * 
 * 	DROP Portfolio Core implements libraries for the following:
 * 	- Asset Allocation Analytics
 * 	- Transaction Cost Analytics
 * 
 * 	DROP Numerical Core implements libraries for the following:
 * 	- Statistical Learning Library
 * 	- Numerical Optimizer Library
 * 	- Machine Learning Library
 * 	- Spline Builder Library
 * 
 * 	Documentation for DROP is Spread Over:
 * 
 * 	- Main                     => https://lakshmidrip.github.io/DROP/
 * 	- Wiki                     => https://github.com/lakshmiDRIP/DROP/wiki
 * 	- GitHub                   => https://github.com/lakshmiDRIP/DROP
 * 	- Javadoc                  => https://lakshmidrip.github.io/DROP/Javadoc/index.html
 * 	- Technical Specifications => https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal
 * 	- Release Versions         => https://lakshmidrip.github.io/DROP/version.html
 * 	- Community Credits        => https://lakshmidrip.github.io/DROP/credits.html
 * 	- Issues Catalog           => https://github.com/lakshmiDRIP/DROP/issues
 * 	- JUnit                    => https://lakshmidrip.github.io/DROP/junit/index.html
 * 	- Jacoco                   => https://lakshmidrip.github.io/DROP/jacoco/index.html
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   	you may not use this file except in compliance with the License.
 *   
 *  You may obtain a copy of the License at
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  	distributed under the License is distributed on an "AS IS" BASIS,
 *  	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  
 *  See the License for the specific language governing permissions and
 *  	limitations under the License.
 */

/**
 * <i>DC30E_360_ISDA</i> implements the 30E/360 ISDA day count convention.
 *	<br>
 *  <ul>
 *		<li><b>Module</b>        = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics">Analytics</a></li>
 *		<li><b>Package</b>       = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/analytics/daycount">Day Count</a></li>
 *		<li><b>Specification</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/Docs/Internal/FixedIncome">Fixed Income</a></li>
 *  </ul>
 *
 * @author Lakshmi Krishnamurthy
 */

public class DC30E_360_ISDA implements org.drip.analytics.daycount.DCFCalculator {

	/**
	 * Empty DC30E_360_ISDA constructor
	 */

	public DC30E_360_ISDA()
	{
	}

	@Override public java.lang.String baseCalculationType()
	{
		return "DC30E_360_ISDA";
	}

	@Override public java.lang.String[] alternateNames()
	{
		return new java.lang.String[] {"ISMA-30/360", "30E/360 ISDA", "30E/360 (ISDA)", "DC30E_360_ISDA"};
	}

	@Override public double yearFraction (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj,
		final ActActDCParams actactParams,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		DateEOMAdjustment dm = DateEOMAdjustment.MakeDEOMA30E_360_ISDA (iStartDate, iEndDate, bApplyEOMAdj);

		if (null == dm)
			throw new java.lang.Exception ("DC30E_360::yearFraction => Cannot create DateEOMAdjustment!");

		return (360. * (org.drip.analytics.date.DateUtil.Year (iEndDate) -
			org.drip.analytics.date.DateUtil.Year (iStartDate)) + 30. *
				(org.drip.analytics.date.DateUtil.Month (iEndDate) - org.drip.analytics.date.DateUtil.Month
					(iStartDate)) + (org.drip.analytics.date.DateUtil.Date (iEndDate) -
						org.drip.analytics.date.DateUtil.Date (iStartDate)) + dm.posterior() - dm.anterior())
							/ 360.;
	}

	@Override public int daysAccrued (
		final int iStartDate,
		final int iEndDate,
		final boolean bApplyEOMAdj,
		final ActActDCParams actactParams,
		final java.lang.String strCalendar)
		throws java.lang.Exception
	{
		DateEOMAdjustment dm = DateEOMAdjustment.MakeDEOMA30E_360_ISDA (iStartDate, iEndDate, bApplyEOMAdj);

		if (null == dm)
			throw new java.lang.Exception ("DC30E_360::daysAccrued => Cannot create DateEOMAdjustment!");

		return 360 * (org.drip.analytics.date.DateUtil.Year (iEndDate) -
			org.drip.analytics.date.DateUtil.Year (iStartDate)) + 30 *
				(org.drip.analytics.date.DateUtil.Month (iEndDate) - org.drip.analytics.date.DateUtil.Month
					(iStartDate)) + (org.drip.analytics.date.DateUtil.Date (iEndDate) -
						org.drip.analytics.date.DateUtil.Date (iStartDate)) + dm.posterior() - dm.anterior();
	}
}
