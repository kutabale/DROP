
package org.drip.state.fx;

/*
 * -*- mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 */

/*!
 * Copyright (C) 2019 Lakshmi Krishnamurthy
 * Copyright (C) 2018 Lakshmi Krishnamurthy
 * Copyright (C) 2017 Lakshmi Krishnamurthy
 * Copyright (C) 2016 Lakshmi Krishnamurthy
 * Copyright (C) 2015 Lakshmi Krishnamurthy
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
 * <i>FXCurve</i> is the Stub for the FX Curve for the specified Currency Pair.
 *
 *  <br><br>
 *  <ul>
 *		<li><b>Module </b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/AnalyticsCore.md">Analytics Core Module</a></li>
 *		<li><b>Library</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/FixedIncomeAnalyticsLibrary.md">Fixed Income Analytics Library</a></li>
 *		<li><b>Project</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state">State</a></li>
 *		<li><b>Package</b> = <a href = "https://github.com/lakshmiDRIP/DROP/tree/master/src/main/java/org/drip/state/fx">FX</a></li>
 *  </ul>
 * <br><br>
 *
 * @author Lakshmi Krishnamurthy
 */

public abstract class FXCurve implements org.drip.analytics.definition.Curve {
	private org.drip.product.params.CurrencyPair _cp = null;

	protected int _iEpochDate = java.lang.Integer.MIN_VALUE;

	protected FXCurve (
		final int iEpochDate,
		final org.drip.product.params.CurrencyPair cp)
		throws java.lang.Exception
	{
		if (null == (_cp = cp)) throw new java.lang.Exception ("FXCurve ctr: Invalid Inputs");

		_iEpochDate = iEpochDate;
	}

	/**
	 * Calculate the FX Forward to the given Date
	 * 
	 * @param iDate Date
	 * 
	 * @return The FX Forward
	 * 
	 * @throws java.lang.Exception Thrown if the FX Forward cannot be calculated
	 */

	public abstract double fx (
		final int iDate)
		throws java.lang.Exception;

	/**
	 * Calculate the set of Zero basis given the input discount curves
	 * 
	 * @param aiDateNode Array of Date Nodes
	 * @param valParams Valuation Parameters
	 * @param dcNum Discount Curve Numerator
	 * @param dcDenom Discount Curve Denominator
	 * @param bBasisOnDenom True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed basis
	 */

	public abstract double[] zeroBasis (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom);

	/**
	 * Bootstrap the basis to the discount curve inputs
	 * 
	 * @param aiDateNode Array of Date Nodes
	 * @param valParams Valuation Parameters
	 * @param dcNum Discount Curve Numerator
	 * @param dcDenom Discount Curve Denominator
	 * @param bBasisOnDenom True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed basis
	 */

	public abstract double[] bootstrapBasis (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom);

	/**
	 * Bootstrap the discount curve from the discount curve inputs
	 * 
	 * @param aiDateNode Array of Date Nodes
	 * @param valParams Valuation Parameters
	 * @param dcNum Discount Curve Numerator
	 * @param dcDenom Discount Curve Denominator
	 * @param bBasisOnDenom True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed basis
	 */

	public abstract org.drip.state.discount.MergedDiscountForwardCurve bootstrapBasisDC (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom);

	/**
	 * Calculate the rates implied by the discount curve inputs
	 * 
	 * @param aiDateNode Array of Date Nodes
	 * @param valParams Valuation Parameters
	 * @param dcNum Discount Curve Numerator
	 * @param dcDenom Discount Curve Denominator
	 * @param bBasisOnDenom True if the basis is calculated on the denominator discount curve
	 * 
	 * @return Array of the computed implied rates
	 */

	public abstract double[] impliedNodeRates (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final boolean bBasisOnDenom);

	/**
	 * Calculate the rate implied by the discount curve inputs to a specified date
	 * 
	 * @param aiDateNode Array of Date Nodes
	 * @param valParams ValuationParams
	 * @param dcNum Discount Curve Numerator
	 * @param dcDenom Discount Curve Denominator
	 * @param iDate Date to which the implied rate is sought
	 * @param bBasisOnDenom True if the implied rate is calculated on the denominator discount curve
	 * 
	 * @return Implied rate
	 * 
	 * @throws java.lang.Exception Thrown if the implied rate cannot be calculated
	 */

	public abstract double rate (
		final int[] aiDateNode,
		final org.drip.param.valuation.ValuationParams valParams,
		final org.drip.state.discount.MergedDiscountForwardCurve dcNum,
		final org.drip.state.discount.MergedDiscountForwardCurve dcDenom,
		final int iDate,
		final boolean bBasisOnDenom)
		throws java.lang.Exception;

	@Override public org.drip.state.identifier.LatentStateLabel label()
	{
		return org.drip.state.identifier.FXLabel.Standard (_cp);
	}

	@Override public java.lang.String currency()
	{
		return _cp.quoteCcy();
	}

	@Override public org.drip.analytics.date.JulianDate epoch()
	{
		return new org.drip.analytics.date.JulianDate (_iEpochDate);
	}

	/**
	 * Return the CurrencyPair
	 * 
	 * @return CurrencyPair
	 */

	public org.drip.product.params.CurrencyPair currencyPair()
	{
		return _cp;
	}

	/**
	 * Calculate the FX Forward to the given date
	 * 
	 * @param dt Date
	 * 
	 * @return The FX Forward
	 * 
	 * @throws java.lang.Exception Thrown if the FX Forward cannot be calculated
	 */

	public double fx (
		final org.drip.analytics.date.JulianDate dt)
		throws java.lang.Exception
	{
		if (null == dt) throw new java.lang.Exception ("FXCurve::fx got null for date");

		return fx (dt.julian());
	}

	/**
	 * Calculate the FX Forward to the given date
	 * 
	 * @param strTenor The Tenor
	 * 
	 * @return The FX Forward
	 * 
	 * @throws java.lang.Exception Thrown if the FX Forward cannot be calculated
	 */

	public double fx (
		final java.lang.String strTenor)
		throws java.lang.Exception
	{
		if (null == strTenor || strTenor.isEmpty())
			throw new java.lang.Exception ("FXCurve::fx got bad tenor");

		return fx (epoch().addTenor (strTenor));
	}

	@Override public boolean setCCIS (
		final org.drip.analytics.input.CurveConstructionInputSet ccis)
	{
		return true;
	}

	@Override public org.drip.product.definition.CalibratableComponent[] calibComp()
	{
		return null;
	}

	@Override public org.drip.analytics.support.CaseInsensitiveTreeMap<java.lang.Double> manifestMeasure (
		final java.lang.String strInstr)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState parallelShiftManifestMeasure (
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState shiftManifestMeasure (
		final int iSpanIndex,
		final java.lang.String strManifestMeasure,
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState customTweakManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState parallelShiftQuantificationMetric (
		final double dblShift)
	{
		return null;
	}

	@Override public org.drip.state.representation.LatentState customTweakQuantificationMetric (
		final org.drip.param.definition.ManifestMeasureTweak rvtp)
	{
		return null;
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param strManifestMeasure Manifest Measure
	 * @param iDate Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public abstract org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final int iDate);

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the given date
	 * 
	 * @param strManifestMeasure Manifest Measure
	 * @param dt Date
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the given date
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final org.drip.analytics.date.JulianDate dt)
	{
		if (null == dt) return null;

		return jackDForwardDManifestMeasure (strManifestMeasure, dt.julian());
	}

	/**
	 * Retrieve the Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 * 
	 * @param strManifestMeasure Manifest Measure
	 * @param strTenor Tenor
	 * 
	 * @return The Manifest Measure Jacobian of the Forward Rate to the date implied by the given Tenor
	 */

	public org.drip.numerical.differentiation.WengertJacobian jackDForwardDManifestMeasure (
		final java.lang.String strManifestMeasure,
		final java.lang.String strTenor)
	{
		if (null == strTenor || strTenor.isEmpty()) return null;

		try {
			return jackDForwardDManifestMeasure (strManifestMeasure, epoch().addTenor (strTenor));
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
