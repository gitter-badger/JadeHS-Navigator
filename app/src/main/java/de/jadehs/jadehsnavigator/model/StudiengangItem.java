/**
 * This file is part of JadeHS-Navigator.
 *
 * JadeHS-Navigator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * JadeHS-Navigator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with JadeHS-Navigator.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jadehs.jadehsnavigator.model;

public class StudiengangItem {
    private String studiengangName;
    private String studiengangID;

    public StudiengangItem(String studiengangName, String studiengangID) {
        this.studiengangName = studiengangName;
        this.studiengangID = studiengangID;
    }

    public String getStudiengangName() {
        return studiengangName;
    }

    public void setStudiengangName(String studiengangName) {
        this.studiengangName = studiengangName;
    }

    public String getStudiengangID() {
        return studiengangID;
    }

    public void setStudiengangID(String studiengangID) {
        this.studiengangID = studiengangID;
    }
}
