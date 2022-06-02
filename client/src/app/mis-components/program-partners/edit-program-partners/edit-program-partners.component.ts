import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "../../../services/alert";
import {ProgramPartnersService} from "../../../services/program-partners.service";
import {CountriesService} from "../../../services/countries.service";

@Component({
  selector: 'app-edit-program-partners',
  templateUrl: './edit-program-partners.component.html',
  styleUrls: ['../program-partners.component.css']
})
export class EditProgramPartnersComponent implements OnInit {

  formGroup: FormGroup;
  submitted = false;
  formData: any;
  programs = [];
  programPartnerId: any;
  countries = [];
  cities = [];
  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private alertService: AlertService,
              private countriesService: CountriesService,
              private router: Router,
              private programPartnersService: ProgramPartnersService) { }

  ngOnInit(): void {
    this.countries = this.countriesService.getListOfCountries();
    this.programPartnerId = this.route.snapshot.params.id;
    this.programPartnersService.getCurrentProgramPartner(this.programPartnerId).subscribe((results: any) => {
      this.formGroup = this.formBuilder.group({
        program: [results?.programId, [Validators.required]],
        cluster: [results?.cluster, [Validators.required]],
        organisation: [results?.organisation],
        physicalAddress: [results?.physicalAddress],
        emailContactPerson: [results?.emailContactPerson],
        telephoneContactPerson: [results?.telephoneContactPerson],
        organisationType: [results?.organisationType],
        country: [results?.country],
        nameContactPerson: [results?.nameContactPerson],
        city: [results?.city]
      });
    });
    this.programPartnersService.getPrograms().subscribe((data) => {
      this.programs = data;
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  editProgramPartner() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
    const programPartner = this.formGroup.value;
    this.programPartnersService.updateProgramPartner(this.programPartnerId, programPartner).subscribe(results => {
      this.router.navigate(['/programPartner']);
      this.alertService.success(`${programPartner.name} has been successfully updated `);
    }, error => {
      this.alertService.error(`${programPartner.name} could not be updated`);
    });

    if (this.formGroup.valid) {
      setTimeout(() => {
        this.formGroup.reset();
        this.submitted = false;
      }, 100);
    }
  }

  onSelectCountry(country) {
    this.countriesService.getCitiesForCountry(country).subscribe((response) => {
      this.cities = response.data;
    }, error => console.log(error))
  }

  cancel(): void {
    window.history.back();
  }

}
