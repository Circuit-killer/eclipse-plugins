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
   @file     UrtLib.h
   @brief    Set of UART peripheral functions.
   - Configure the UART pins by setting the mux options in GPCON
   - Configure UART with UrtCfg().
   - Set modem control with UrtMod() if desired.
   - Check space in Tx buffer with UrtLinSta().
   - Output character with UrtTx().
   - Read characters with UrtRx().

   @version  V0.3
   @author   ADI
   @date     October 2012
   @par Revision History:
   - V0.1, March 2012: initial version.
   - V0.2, October 2012: Fixed Baud rate generation function
   - V0.3, October 2015: Coding style cleanup - no functional changes.

**/

#include <ADuCM361.h>

extern int UrtCfg(ADI_UART_TypeDef *pPort, int iBaud, int iBits, int iFormat);
extern int UrtBrk(ADI_UART_TypeDef *pPort, int iBrk);
extern int UrtLinSta(ADI_UART_TypeDef *pPort);
extern int UrtTx(ADI_UART_TypeDef *pPort, int iTx);
extern int UrtRx(ADI_UART_TypeDef *pPort);
extern int UrtMod(ADI_UART_TypeDef *pPort, int iMcr, int iWr);
extern int UrtModSta(ADI_UART_TypeDef *pPort);
extern int UrtDma(ADI_UART_TypeDef *pPort, int iDmaSel);
extern int UrtIntCfg(ADI_UART_TypeDef *pPort, int iIrq);
extern int UrtIntSta(ADI_UART_TypeDef *pPort);


// baud rate settings
#define B1200  1200
#define B2200  2200
#define B2400  2400
#define B4800  4800
#define B9600  9600
#define B19200 19200
#define B38400 38400
#define B57600 57600
#define B115200   115200
#define B230400   230400
#define B430800   430800
