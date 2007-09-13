/*
 * Copyright (C) 2005-2007 Les Hazlewood
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the
 *
 * Free Software Foundation, Inc.
 * 59 Temple Place, Suite 330
 * Boston, MA 02111-1307
 * USA
 *
 * Or, you may view it online at
 * http://www.opensource.org/licenses/lgpl-license.php
 */
package org.jsecurity.authc.event.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsecurity.authc.AuthenticationException;
import org.jsecurity.authc.LockedAccountException;
import org.jsecurity.authc.event.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Simple implementation of the AuthenticationEventListener interface that only logs the events received to an
 * internal Jakarta Commons Logging <tt>Log</tt> attribute.
 *
 * <p>This class should serve as an example to people writing listener implementations to handle events in a
 * custom application.
 *
 * @since 0.2
 * @author Les Hazlewood
 */
public class LoggingAuthenticationEventListener implements AuthenticationEventListener {

    protected static final DateFormat ISO8601_DATE_FORMAT = new SimpleDateFormat( "YYYY-MM-dd HH:mm:ss.S" );

    protected transient final Log log = LogFactory.getLog( getClass() );

    public LoggingAuthenticationEventListener() {
    }

    public void onEvent( AuthenticationEvent event ) {
        //only incur stack execution overhead if the messages would actually be printed to the log:
        if ( log.isDebugEnabled() ) {
            accept( event );
        }
    }

    /**
     * Notification callback that an account has authenticated successfully.
     * @param event the event associated with the successful authentication attempt.
     */
    protected void accept( SuccessfulAuthenticationEvent event ) {
        String msg = "Subject with principal [" + event.getPrincipal() + "] successfully authenticated on [" +
            ISO8601_DATE_FORMAT.format( event.getTimestamp() ) + "]";
        log.debug( msg );
    }

    /**
     * Notification callback that an account has logged-out.
     * @param event the event associated with the log-out.
     */
    protected void accept( LogoutEvent event ) {
        String msg = "Subject with principal [" + event.getPrincipal() + "] logged out on [" +
            ISO8601_DATE_FORMAT.format( event.getTimestamp() ) + "]";
        log.debug( msg );
    }

    /**
     * Notification callback that an account has been locked from further authentication
     * attempts (usually due to too many failed authentication attempts).
     * @param event the event generated due to an account being locked.
     */
    protected void accept( UnlockedAccountEvent event ) {
        String msg = "The account for the subject with principal [" + event.getPrincipal() + "] was unlocked on [" +
            ISO8601_DATE_FORMAT.format( event.getTimestamp() ) + "]";
        log.debug( msg );
    }

    /**
     * Notification callback that an account authentication attempt had failed.
     * @param event the event associated with the failed authentication attempt.
     */
    protected void accept( FailedAuthenticationEvent event ) {
        AuthenticationException cause = event.getCause();

        if ( cause != null && ( cause instanceof LockedAccountException ) ) {
            String msg = "The account for the subject with principal [" + event.getPrincipal() + "] was locked on [" +
                ISO8601_DATE_FORMAT.format( event.getTimestamp() ) + "]";
            log.debug( msg );
        } else {
            String msg = "Login attempt for subject with principal [" + event.getPrincipal() + "] failed on [" +
                ISO8601_DATE_FORMAT.format( event.getTimestamp() ) + "]";
            if ( cause != null ) {
                msg += ".  Cause: ";
                log.debug( msg, cause );
            } else {
                log.debug( msg );
            }
        }
    }

    protected void accept( AuthenticationEvent event ) {
        String msg = "Received unrecognized event of type [" + event.getClass().getName() + "] for subject with " +
            "principal [" + event.getPrincipal() + "].";
        log.debug( msg );
    }
}
