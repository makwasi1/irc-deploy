import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AlertService} from "../../../services/alert";
import {ProgramPartnersService} from "../../../services/program-partners.service";
import {CountriesService} from "../../../services/countries.service";
import {v4 as uuid} from 'uuid';
import {CellEdit, OnUpdateCell} from "../../../helpers/cell-edit";

@Component({
  selector: 'app-create-program-partners',
  templateUrl: './create-program-partners.component.html',
  styleUrls: ['../program-partners.component.css']
})
export class CreateProgramPartnersComponent implements OnInit, OnUpdateCell {

  formGroup: FormGroup;
  submitted = false;
  formData: any;
  programs = [];
  countries = [];
  cities = [];
  organisationsInvolved: any = [];

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private alertService: AlertService,
              private countriesService: CountriesService,
              private router: Router,
              private programPartnersService: ProgramPartnersService) {
  }

  ngOnInit(): void {
    this.countries = this.countriesService.getListOfAvailableCountries();
    this.formGroup = this.formBuilder.group({
      program: [''],
      cluster: [''],
      organisation: [''],
      physicalAddress: [''],
      organisationType: [''],
      nameContactPerson: [''],
      telephoneContactPerson: [''],
      emailContactPerson: [''],
      country: [''],
      city: [''],
      dataCollector: [''],
      organisationsInvolved: ['']
    });
    this.programPartnersService.getPrograms().subscribe((data) => {
      this.programs = data;
    });
    this.programPartnersService.getDataCollector().subscribe(res => {
      this.formGroup.patchValue({dataCollector: res.user_id});
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  createProgramPartner() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }

    this.formGroup.patchValue({organisationsInvolved: JSON.stringify(this.organisationsInvolved)});
    const programPartner = this.formGroup.value;
    console.log("Form Data", programPartner)
    this.programPartnersService.createProgramPartner(programPartner).subscribe(results => {
      this.alertService.success(`${programPartner.cluster} has been successfully created `);
    }, error => {
      this.alertService.error(`${programPartner.cluster} could not be created`);
    });

    if (this.formGroup.valid) {
      setTimeout(() => {
        this.formGroup.reset();
        this.submitted = false;
        this.router.navigate(['/programPartner']);
      }, 100);
    }
  }

  onSelectCountry(country) {
    this.countriesService.getCitiesForCountry(country).subscribe((response) => {
      this.cities = response.data;
    }, error => console.log(error))
  }

  onReset() {
    this.formGroup.reset();
  }

  addOrganization() {
    if (this.organisationsInvolved.length < 5) {
      let id = uuid();
      this.organisationsInvolved.push({id: id, name: '', contact: ''});
    }
  }

  cellEditor(rowId, tdId, key: string, oldValue) {
    new CellEdit().edit(rowId, tdId, oldValue, key, this.saveCellValue);
  }

  saveCellValue = (value: string, key: string, rowId): void => {
    if (value !== null && value !== undefined)
      switch (key) {
        case 'orgName':
          if (this.organisationsInvolved.some(x => x.id === rowId)) {
            this.organisationsInvolved.forEach(function (item) {
              if (item.id === rowId) {
                item.name = value
              }
            });
          }
          break;
        case 'orgContact':
          if (this.organisationsInvolved.some(x => x.id === rowId)) {
            this.organisationsInvolved.forEach(function (item) {
              if (item.id === rowId) {
                item.contact = value
              }
            });
          }
          break;
      }
  }

}
