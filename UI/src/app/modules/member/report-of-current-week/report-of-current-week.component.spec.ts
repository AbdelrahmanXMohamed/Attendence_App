import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportOfCurrentWeekComponent } from './report-of-current-week.component';

describe('ReportOfCurrentWeekComponent', () => {
  let component: ReportOfCurrentWeekComponent;
  let fixture: ComponentFixture<ReportOfCurrentWeekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportOfCurrentWeekComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportOfCurrentWeekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
