import { DailyStatus } from "./DailyStatus";
import { User } from "./User";

export interface Status {
    id: number;
    day: Date;
    user: User;
    status: DailyStatus;
  }
  