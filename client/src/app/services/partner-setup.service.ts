import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PartnerSetupService {

  constructor(private http: HttpClient) { }

  getPartnerSetup(): Observable<any> {
    return this.http.get(`${environment.serverUrl}/partnerSetup`);
  }

  createPartnerSetup(formData): Observable<any> {
    return this.http.post(`${environment.serverUrl}/partnerSetup`, formData);
  }

  updatePartnerSetup(formData, id): Observable<any> {
    return this.http.put(`${environment.serverUrl}/partnerSetup/${id}`, formData);
  }

  getPartnerSetupRecord(params: any): Observable<any> {
    return this.http.get(`${environment.serverUrl}/partnerSetup/getPartnerSetupRecord`, {params});
  }

  deletePartnerSetupRecord(deletedRow): Observable<any> {
    return this.http.delete(`${environment.serverUrl}/partnerSetup/${deletedRow}`);
  }

  getReportingCalendarByPartnerSetupId(params: any): Observable<any> {
    return this.http.get(`${environment.serverUrl}/calendarTriggerDates/getReportingCalendarByPartnerSetupId`, {params});
  }

  updateReportingCalendar(formData, id): Observable<any> {
    return this.http.put(`${environment.serverUrl}/calendarTriggerDates/${id}`, formData);
  }

  createReportingCalendar(formData): Observable<any> {
    return this.http.post(`${environment.serverUrl}/calendarTriggerDates`, formData);
  }
}
