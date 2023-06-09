import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: "root",
})
export class UsersService {
  baseurl = environment.serverUrl;
  urlUsers = `${this.baseurl}/user/`;
  urlMISUsers = `${this.baseurl}/getMISUsers/`;
  urlUserGroup = `${this.baseurl}/KengaUserGroup/`;
  urlUserRole = `${this.baseurl}/UserRole/`;
  urlUserPartner = `${this.baseurl}/UserPartner/`;
  urlDeleteOldRolesAndGroups = `${this.baseurl}/deleteOldRoleAndGroups/`;
  getUserGroupUrl = `${this.baseurl}/getUserGroupUsingPartner/`;

  constructor(private http: HttpClient) {}

  getUsers(): Observable<any> {
    return this.http.get(this.urlUsers);
  }

  getUserGroup(params: HttpParams): Observable<any> {
    return this.http.get(this.getUserGroupUrl, { params });
  }

  getMISUsers(): Observable<any> {
    return this.http.get(this.urlMISUsers);
  }

  getDataCollectors(): Observable<any> {
    return this.http.get(`${this.urlUsers}/getDataCollectors`);
  }

  getUsersFiltered(params): Observable<any> {
    return this.http.get(this.urlUsers, { params });
  }

  createUser(formData): Observable<any> {
    return this.http.post(this.urlUsers, formData);
  }

  updateUser(id, groupData, params): Observable<any> {
    return this.http.put(`${this.urlUsers}${id}/`, groupData, { params });
  }
  deleteOldRolesAndGroups(params) {
    return this.http.delete(this.urlDeleteOldRolesAndGroups, { params });
  }

  getCurrentUser(id) {
    return this.http.get(`${this.urlUsers}${id}`);
  }

  deleteCurrentUser(p): Observable<any> {
    return this.http.delete(`${this.urlUsers}${p}/`);
  }

  // this is for creating records in the user group table
  createUserGroup(userGroupData): Observable<any> {
    return this.http.post(this.urlUserGroup, userGroupData);
  }

  // this is for creating records in the user role table
  createUserRole(userRoleData): Observable<any> {
    return this.http.post(this.urlUserRole, userRoleData);
  }

  createUserPartner(userPartnerData): Observable<any> {
    return this.http.post(this.urlUserPartner, userPartnerData);
  }
}
