import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ModalDismissReasons, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpParams} from "@angular/common/http";
import {AlertService} from "../../services/alert";
import {TagService} from "../../services/tags";
import {EntityService} from "../../services/entity.service";

@Component({
  selector: 'app-tag-type',
  templateUrl: './tag-type.component.html',
  styleUrls: ['./tag-type.component.css']
})
export class TagTypeComponent implements OnInit {

  entries: number = 10;
  selected: any[] = [];
  activeRow: any;
  tagTypeId = '';
  search = '';
  private searchValue = '';
  tagTypes: Object[];
  closeResult: string;
  formGroup: FormGroup;
  formGp: FormGroup;
  rowData: any;
  submitted = false;
  misEntities = [];
  rows: Object[];
  temp: Object[];

  constructor(private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private alertService: AlertService,
              private router: Router,
              private modalService: NgbModal,
              private tagService: TagService,
              private entityService: EntityService) {
  }

  ngOnInit(): void {
    this.reloadTable();
    this.formGroup = this.formBuilder.group({
      name: ['', Validators.required],
      misEntity: ['', Validators.required]
    });
    this.entityService.getEntities().subscribe((data) => {
      this.misEntities = data;
    });
  }

  get f() {
    return this.formGroup.controls;
  }

  addTagType() {
    this.submitted = true;
    if (this.formGroup.invalid) {
      console.log('Invalid');
      return;
    }
    const newTagType = this.formGroup.value;
    this.tagService.addNewTagType(newTagType).subscribe(results => {
      this.alertService.success(`Tag Type: ${results.name} has been successfully created `);
      this.reloadTable();
      this.formGroup.reset();
    }, error => {
      this.alertService.error(`Tag Type: ${this.formGroup.controls.name.value} could not be created`);
    });
    this.modalService.dismissAll('Dismissed after saving data');
    this.router.navigate(['/tagType']);

    if (this.formGroup.valid) {
      setTimeout(() => {
        this.formGroup.reset();
        this.submitted = false;
      }, 100);
    }
  }

  editTagType(row) {
    const id = row.id;
    this.router.navigate(['/tagType/edit/'+ id]);
  }

  deleteTagType(row) {
    const deletedRow = row.id;
    if (confirm('Are you sure to delete this Tag Type?')) {
      this.tagService.deleteTagType(deletedRow).subscribe((result) => {
          this.alertService.warning(`Tag Type has been  deleted `);
          this.router.navigate(['/tagType']);
          this.reloadTable();
        }, error => {
          this.alertService.error(`Tag Type: ${this.formGp.controls.name.value} could not be deleted`)
        }
      );
    }
  }

  /*Responsible for the opening of the Modals*/
  open(content) {
    this.modalService.open(content,
      {
        ariaLabelledBy: 'modal-basic-title'
      }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      }, (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      });
  }

  /*Responsible for Closing the Modal*/
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  onChangeSearch(event) {
    let val = event.target.value.toLowerCase();
    // update the rows
    this.rows = this.temp.filter(function (d) {
      for (const key in d) {
        if (d[key]?.toLowerCase().indexOf(val) !== -1) {
          return true;
        }
      }
      return false;
    });
  }

  reloadTable() {
    this.tagService.getTagTypes().subscribe((data) => {
      this.temp = [...data];
      this.rows = data;
    });
  }

  entriesChange($event) {
    this.entries = $event.target.value;
    this.reloadTable();
  }

  onActivate(event) {
    this.activeRow = event.row;
  }

  filterTable($event) {
    this.search = $event.target.value;
    this.reloadTable();
  }

  onSelect({selected}) {
    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }

  onSearch(event) {
    this.reloadTable();
  }
}
