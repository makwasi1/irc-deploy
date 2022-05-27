import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CountriesService} from "../../../services/countries.service";

@Component({
  selector: 'long-term-application',
  templateUrl: './long-term-application.component.html',
  styleUrls: ['../grant-process.component.css']
})

export class LongTermApplicationComponent implements OnInit {

  formGroup: FormGroup;
  submitted = false;
  organizationType = [
    {id:"1", name:"my type"}
  ] ;
  countries: any;
  pCountries: any;
  cities: any;
  pCities: any;
  programs = [
    {id:"1", name:"Adolescent Girl Power Program"},
    {id:"2", name:"Youth Capacity Development Program"},
    {id:"3", name:"Prevention of Violence Against Children and Adolescents"},
  ] ;
  radioEndOfPartnership: any;

  constructor(
    private countriesService: CountriesService,
    private formBuilder: FormBuilder
  ) { }

  get f() {
    return this.formGroup.controls;
  }

  ngOnInit(): void {
    this.countries = this.countriesService.getListOfCountries();

    this.formGroup = this.formBuilder.group({
      program: ['', [Validators.required]],
      organisation: ['', [Validators.required]],
      acronym: [null],
      organization_type: ['', [Validators.required]],
      legal_status: ['', [Validators.required]],
      contact_person: ['', [Validators.required]],
      address_contact_person: ['', [Validators.required]],
      email_address: ['', [Validators.required]],
      contact_person_number: ['', [Validators.required]],
      physical_address: ['', [Validators.required]],
      postal_address: ['', [Validators.required]],
      email: ['', [Validators.required]],
      website: [null],
      country: [null],
      city: [null],
      attachment: [null],

      project_duration: [null],
      project_country: [null],
      project_city: [null],
      project_proposed: [null],
      project_amount: [null],
      amount_requested: [null],
      funding: [null],

      completed: [null],
      mel_framework: [null],
      financial: [null],
      registration: [null],
      list_members: [null],
      assessment_report: [null],
      strategic_plan: [null],
      annual_work_plan: [null],
      child_policy: [null],
      structure: [null],

    });
  }

  submitLetter() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
    const formData = this.formGroup.value;
    console.log('formData', formData)
  }

  onSelectCountry(country){
    this.countriesService.getCitiesForCountry(country).subscribe((response)=>{
      this.cities = response.data;
    }, error => console.log(error))
  }

  onSelectProjectCountry(country){
    this.countriesService.getCitiesForCountry(country).subscribe((response)=>{
      this.pCities = response.data;
    }, error => console.log(error))
  }

  cancel() {
    this.formGroup.reset()
    this.submitted = false
  }
}
