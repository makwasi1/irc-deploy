import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateFeedbackComponent } from './create-feedback.component';

describe('CreateSystemUserComponent', () => {
  let component: CreateFeedbackComponent;
  let fixture: ComponentFixture<CreateFeedbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateFeedbackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});