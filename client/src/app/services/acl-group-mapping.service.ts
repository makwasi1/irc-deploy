import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AclGroupMappingService {
  baseurl = environment.serverUrl;
  urlMapping = `${this.baseurl}/saveQueryToTable/`;
  urlAcl = `${this.baseurl}/kengaGroupAclEntry/`;

  constructor(private http: HttpClient) { }

  listAllACLS(): Observable<any> {
    return this.http.get(`${this.baseurl}/queryTable/`);
  }

  createGroupMapping2(formData): Observable<any> {
    return this.http.post(this.urlMapping, formData);
  }

  deleteCurrentACL(p): Observable<any> {
    return this.http.delete(`${this.urlAcl}${p}/`);
  }
  getCurrentQuery(id) {
    return this.http.get(`${this.baseurl}/queryTable/${id}/`);
  }

}
