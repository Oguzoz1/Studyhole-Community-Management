import { TestBed } from '@angular/core/testing';

import { ChooseTemplateService } from './choose-template.service';

describe('ChooseTemplateService', () => {
  let service: ChooseTemplateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChooseTemplateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
