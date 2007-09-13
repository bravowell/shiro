/*
* Copyright (C) 2005-2007 Jeremy Haile, Les Hazlewood
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

package org.jsecurity.authc.credential.commonsdigest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Digest password matcher that uses the SHA-1 hashing algorithm
 * to hash the provided password.
 *
 * @since 0.1
 * @author Jeremy Haile
 * @author Les Hazlewood
 */
public class ShaCredentialMatcher extends DigestCredentialMatcher {

    /**
     * Returns an SHA-1 hash of the given argument.
     *
     * @param providedPassword the value to hash
     * @return an SHA-1 hash of the given argument.
     */
    protected byte[] doDigest(byte[] providedPassword) {
        return DigestUtils.sha( providedPassword );
    }

}