import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class GrantProcessService {
  letterOfInterest = `${environment.serverUrl}/grantLetterOfInterest`
  reviewLetterOfInterest = `${environment.serverUrl}/grantLetterOfInterestReview`;
  planningLearning = `${environment.serverUrl}/grantPlanningLearning`;
  planningLearningReview = `${environment.serverUrl}/grantPlanningLearningReview`;
  planningLearningApprove = `${environment.serverUrl}/grantPlanningLearningApprove`;
  provideLearningGrant = `${environment.serverUrl}/grantProvideLearningGrant`;
  grantReport = `${environment.serverUrl}/grantReport`;
  grantReportReview = `${environment.serverUrl}/grantReportReview`;

  constructor(private http: HttpClient) {
  }

  /***
   *   get
   */

  getAllLetterOfInterest() {
    return this.http.get(`${this.letterOfInterest}`);
  }

  getLetterOfInterest(id) {
    return this.http.get(`${this.letterOfInterest}/${id}/`);
  }

  getLetterOfInterestReview(id) {
    return this.http.get(`${this.reviewLetterOfInterest}/${id}/`);
  }

  getPlanningAndLearningRecord(id) {
    return this.http.get(`${this.planningLearning}/${id}/`);
  }

  getPlanningAndLearningReview(id) {
    return this.http.get(`${this.planningLearningReview}/${id}/`);
  }

  getPlanningAndLearningApprove(id) {
    return this.http.get(`${this.planningLearningApprove}/${id}/`);
  }

  getProvideLearningGrant(id) {
    return this.http.get(`${this.provideLearningGrant}/${id}/`);
  }

  getGrantReport(id) {
    return this.http.get(`${this.grantReport}/${id}/`);
  }

  getGrantReportReview(id) {
    return this.http.get(`${this.grantReportReview}/${id}/`);
  }


  /***
   * get by Process Instance Id
   * */
  getRecordByProcessInstanceId(route, params: any): Observable<any> {
    return this.http.get(route, {params});
  }

  /***
   *   create
   */


  createLetterOfInterest(formData): Observable<any> {
    return this.http.post(this.letterOfInterest, formData);
  }

  createReviewLetterOfInterest(formData): Observable<any> {
    return this.http.post(this.reviewLetterOfInterest, formData);
  }

  createPlanningAndLearningRecord(formData): Observable<any> {
    return this.http.post(this.planningLearning, formData);
  }

  createPlanningAndLearningReview(formData): Observable<any> {
    return this.http.post(this.planningLearningReview, formData);
  }

  createPlanningAndLearningApprove(formData): Observable<any> {
    return this.http.post(this.planningLearningApprove, formData);
  }

  createProvideLearningGrant(formData): Observable<any> {
    return this.http.post(this.provideLearningGrant, formData);
  }

  createGrantReport(formData): Observable<any> {
    return this.http.post(this.grantReport, formData);
  }

  createGrantReportReview(formData): Observable<any> {
    return this.http.post(this.grantReportReview, formData);
  }

  /***
   *   update
   */

  updateLetterOfInterest(formData, id): Observable<any> {
    return this.http.put(`${this.letterOfInterest}/${id}`, formData);
  }

  updateLetterOfInterestReview(formData, id): Observable<any> {
    return this.http.put(`${this.reviewLetterOfInterest}/${id}`, formData);
  }

  updatePlanningAndLearningRecord(formData, id): Observable<any> {
    return this.http.put(`${this.planningLearning}/${id}`, formData);
  }

  updatePlanningAndLearningReview(formData, id): Observable<any> {
    return this.http.put(`${this.planningLearningReview}/${id}`, formData);
  }

  updatePlanningAndLearningApprove(formData, id): Observable<any> {
    return this.http.put(`${this.planningLearningApprove}/${id}`, formData);
  }

  updateProvideLearningGrant(formData, id): Observable<any> {
    return this.http.put(`${this.provideLearningGrant}/${id}`, formData);
  }

  updateGrantReport(formData, id): Observable<any> {
    return this.http.put(`${this.grantReport}/${id}`, formData);
  }

  updateGrantReportReview(formData, id): Observable<any> {
    return this.http.put(`${this.grantReportReview}/${id}`, formData);
  }
}
