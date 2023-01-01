import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportByRangeComponent } from './report-by-range.component';

describe('ReportByRangeComponent', () => {
  let component: ReportByRangeComponent;
  let fixture: ComponentFixture<ReportByRangeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportByRangeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportByRangeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
