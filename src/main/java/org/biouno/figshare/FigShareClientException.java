/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 The BioUno team, Bruno P. Kinoshita
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.biouno.figshare;

/**
 * {@link FigShareClient} exception.
 *
 * @since 0.1
 */
public class FigShareClientException extends RuntimeException {

    /*
     * Serial UID.
     */
    private static final long serialVersionUID = 2894960741035518125L;

    /**
     * Default constructor.
     */
    public FigShareClientException() {
    }

    /**
     * Constructor.
     * @param message message
     */
    public FigShareClientException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param cause cause
     */
    public FigShareClientException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * @param message message
     * @param cause cause
     */
    public FigShareClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     * @param message message
     * @param cause cause
     * @param enableSuppression flag to enable suppression
     * @param writableStackTrace flag for writable stack trace
     */
    public FigShareClientException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
