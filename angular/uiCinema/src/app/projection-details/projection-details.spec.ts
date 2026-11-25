import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProjectionDetails } from './projection-details';

describe('ProjectionDetails', () => {
  let component: ProjectionDetails;
  let fixture: ComponentFixture<ProjectionDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectionDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectionDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
