/*******************************************************************************
* Copyright 2015(c) Analog Devices, Inc.
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
*  - Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer.
*  - Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer in
*    the documentation and/or other materials provided with the
*    distribution.
*  - Neither the name of Analog Devices, Inc. nor the names of its
*    contributors may be used to endorse or promote products derived
*    from this software without specific prior written permission.
*  - The use of this software may or may not infringe the patent rights
*    of one or more patent holders.  This license does not release you
*    from the requirement that you obtain separate licenses from these
*    patent holders to use this software.
*  - Use of the software either in source or binary form, must be run
*    on or directly connected to an Analog Devices Inc. component.
*
* THIS SOFTWARE IS PROVIDED BY ANALOG DEVICES "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, NON-INFRINGEMENT, MERCHANTABILITY
* AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
* IN NO EVENT SHALL ANALOG DEVICES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* INTELLECTUAL PROPERTY RIGHTS, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*******************************************************************************/

/**
 *****************************************************************************
   @file       AdcLib.h
   @brief      Set of ADC peripheral functions.
   - Start by setting ADC in idle mode with AdcGo().
   - Set up ADC with AdcRng(), AdcFlt(), AdcMski() and AdcPin().
   - Optionally use AdcBuf() AdcDiag() and AdcBias().
   - Start conversion with AdcGo().
   - Check with AdcSta() that result is available.
   - Read result with AdcRd().
   - Example:

   @version    V0.5
   @author     ADI
   @date       April 2013
   @par Revision History:
   - V0.1, November 2010: initial version.
   - V0.2, September 2012: Fixed AdcDiag() - correct bits are modified now.
                           Fixed AdcRng().
   - V0.3, January 2013:   Fixed AdcFlt()
                           Changed AdcDetCon(), AdcDetSta(), AdcStpRd() to use
                           the pPort passed in.
   - V0.4, April 2013:     Added parameters definitions for AdcBias function
   - V0.5, October 2015:   Coding style cleanup - no functional changes.

**/
#include <ADuCM361.h>

extern int AdcRng(ADI_ADC_TypeDef *pPort, int iRef, int iGain, int iCode);
extern int AdcGo(ADI_ADC_TypeDef *pPort, int iStart);
extern int AdcRd(ADI_ADC_TypeDef *pPort);
extern int AdcBuf(ADI_ADC_TypeDef *pPort, int iRBufCfg, int iBufCfg);
extern int AdcDiag(ADI_ADC_TypeDef *pPort, int iDiag);
extern int AdcBias(ADI_ADC_TypeDef *pPort, int iBiasPin, int iBiasBoost, int iGndSw);
extern int AdcPin(ADI_ADC_TypeDef *pPort, int iInN, int iInP);
extern int AdcFlt(ADI_ADC_TypeDef *pPort, int iSF, int iAF, int iFltCfg);
extern int AdcMski(ADI_ADC_TypeDef *pPort, int iMski, int iWr);
extern int AdcSta(ADI_ADC_TypeDef *pPort);
//extern int AdcDma(ADI_ADC_TypeDef *pPort, int iDmaRdWr);
extern int AdcPGAErr(ADI_ADC_TypeDef *pPort, int iAdcSta);
extern int AdcDetSta(ADI_ADCSTEP_TypeDef *pPort);
extern int AdcDetCon(ADI_ADCSTEP_TypeDef *pPort, int iCtrl, int iAdcSel, int iRate );
extern int AdcStpRd(ADI_ADCSTEP_TypeDef *pPort);
extern int AdcDmaCon(int iChan, int iEnable);



//ADC filter extra features disabled
#define  FLT_NORMAL     0
//VBias boost.
#define  ADC_BIAS_X1    0
#define  ADC_BUF_ON     0
#define  ADC_GND_OFF     0
#define  ADC_BIAS_OFF    0


#define  DETCON_RATE_2ms 0
#define  DETCON_RATE_4ms 1
#define  DETCON_RATE_6ms 2
#define  DETCON_RATE_8ms 3


