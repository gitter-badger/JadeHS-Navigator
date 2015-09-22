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

public class InfoSysItem {
    private long id;
    private String title;
    private String description;
    private String link;
    private String creator;
    private String created;
    private int fb;

    public InfoSysItem(){}

    public InfoSysItem(long id, String title, String description, String link, String creator, String created, int fb){
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.creator = creator;
        this.created = created;
        this.fb = fb;
    }

    public InfoSysItem(String title, String description, String link, String creator, String created, int fb){
        //this.id = null;
        this.title = title;
        this.description = description;
        this.link = link;
        this.creator = creator;
        this.created = created;
        this.fb = fb;
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

    public void setDescription(String text) {
        this.description = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getFB() {
        return fb;
    }

    public void setFB(int fb) {
        this.fb = fb;
    }
}
