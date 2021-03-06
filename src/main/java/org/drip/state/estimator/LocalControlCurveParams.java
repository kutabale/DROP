	
package org.drip.state.estimator;

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
 * <i>LocalControlCurveParams</i> enhances the SmoothingCurveStretchParams to produce locally customized
 * curve smoothing. Flags implemented by LocalControlCurveParams control the following:
 *
 *  <br><br>
 *  <ul>
 *  	<li>
 *  		The C1 generator scheme to be used
 *  	</li>
 *  	<li>
 *  		Whether to eliminate spurious extrema
 *  	</li>
 *  	<li>
 *  		Whether or not to apply monotone filtering
 *  	</li>
 *  </ul>
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/estimator">Estimator</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public class LocalControlCurveParams extends org.drip.state.estimator.SmoothingCurveStretchParams {
	private boolean _bApplyMonotoneFilter = false;
	private boolean _bEliminateSpuriousExtrema = false;
	private java.lang.String _strC1GeneratorScheme = "";

	/**
	 * LocalControlCurveParams constructor
	 * 
	 * @param strC1GeneratorScheme C1 Generator Stretch
	 * @param strSmootheningQuantificationMetric Curve Smoothening Quantification Metric
	 * @param scbc Segment Builder Parameters
	 * @param iCalibrationDetail The Calibration Detail
	 * @param sbfr Curve Fitness Weighted Response
	 * @param sbfrSensitivity Curve Fitness Weighted Response Sensitivity
	 * @param bEliminateSpuriousExtrema TRUE - Eliminate Spurious Extrema
	 * @param bApplyMonotoneFilter TRUE - Apply Monotone Filter
	 * 
	 * @throws java.lang.Exception Thrown if the Inputs are Invalid
	 */

	public LocalControlCurveParams (
		final java.lang.String strC1GeneratorScheme,
		final java.lang.String strSmootheningQuantificationMetric,
		final org.drip.spline.params.SegmentCustomBuilderControl scbc,
		final int iCalibrationDetail,
		final org.drip.spline.params.StretchBestFitResponse sbfr,
		final org.drip.spline.params.StretchBestFitResponse sbfrSensitivity,
		final boolean bEliminateSpuriousExtrema,
		final boolean bApplyMonotoneFilter)
		throws java.lang.Exception
	{
		super (strSmootheningQuantificationMetric, scbc, iCalibrationDetail, sbfr, sbfrSensitivity);

		if (null == (_strC1GeneratorScheme = strC1GeneratorScheme))
			throw new java.lang.Exception ("LocalControlCurveParams ctr: Invalid Inputs!");

		_bApplyMonotoneFilter = bApplyMonotoneFilter;
		_bEliminateSpuriousExtrema = bEliminateSpuriousExtrema;
	}

	/**
	 * Retrieve the Apply Monotone Filter Flag
	 * 
	 * @return The Apply Monotone Filter Flag
	 */

	public boolean applyMonotoneFilter()
	{
		return _bApplyMonotoneFilter;
	}

	/**
	 * Retrieve the Eliminate Spurious Extrema Flag
	 * 
	 * @return The Eliminate Spurious Extrema Flag
	 */

	public boolean eliminateSpuriousExtrema()
	{
		return _bEliminateSpuriousExtrema;
	}

	/**
	 * Retrieve the C1 Generator Scheme
	 * 
	 * @return The C1 Generator Scheme
	 */

	public java.lang.String C1GeneratorScheme()
	{
		return _strC1GeneratorScheme;
	}
}
