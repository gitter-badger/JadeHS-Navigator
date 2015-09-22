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

public class RSSItem {
    private long id;
    private String title;
    private String description;
    private String link;
    private long originID;
    private String created;

    private RSSOrigin origin;

    public RSSItem(){}

    public RSSItem(String title, String description, String link, long originID, String created){
        this.title = title;
        this.description = description;
        this.link = link;
        this.originID = originID;
        this.created = created;
    }

    public RSSItem(long id, String title, String description, String link, long originID, String created){
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.originID = originID;
        this.created = created;
    }

    public RSSItem(String title, String description, String link, RSSOrigin origin, String created){
        this.title = title;
        this.description = description;
        this.link = link;
        this.origin = origin;
        this.created = created;
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getOriginID() {
        return originID;
    }

    public void setOriginID(long originID) {
        this.originID = originID;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public RSSOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(RSSOrigin origin) {
        this.origin = origin;
    }
}
