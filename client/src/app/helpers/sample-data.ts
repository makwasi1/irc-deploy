export class SampleData {
  static users() {
    return [
      {
        'id': 'f30e446f-8680-4a93-93a4-a111ba698a0b',
        'username': 'okellomarvin',
        'first_name': 'Okello',
        'last_name': 'Marvin',
        'telephone': '0786787035',
        'email': 'okellomarvin@gmail.com',
        'is_active': true,
        'mobile_user_id': 'UG17-17',
        'role': 'Data Manager',
        'groups': 'Uganda'
      },
      {
        'id': 'c4c1b7a2-383a-4693-a480-815370416eff',
        'username': 'lewis44',
        'first_name': 'Lewis',
        'last_name': 'Hamilton',
        'telephone': '0786447035',
        'email': 'lewishamilton@gmail.com',
        'is_active': true,
        'mobile_user_id': 'W13-44',
        'role': 'Data Manager',
        'groups': 'United Kingdom'
      },
      {
        'id': '7cfac3ac-a462-4bd8-9d33-e9a6557d4aab',
        'username': 'pierregasly',
        'first_name': 'Pierre',
        'last_name': 'Gasly',
        'telephone': '0786107035',
        'email': 'pierregasly@gmail.com',
        'is_active': true,
        'mobile_user_id': 'AT13-10',
        'role': 'Data Manager',
        'groups': 'France'
      },
      {
        'id': '907e3256-193f-4b9e-9ee4-bdf0626c384c',
        'username': 'goergerussel',
        'first_name': 'Goerge',
        'last_name': 'Russel',
        'telephone': '0786786535',
        'email': 'goergerussel@gmail.com',
        'is_active': true,
        'mobile_user_id': 'W13-66',
        'role': 'Data Manager',
        'groups': 'United Kingdom'
      },
      {
        'id': '8e11e579-15df-4acf-bf2d-545a8f763443',
        'username': 'carlossainz',
        'first_name': 'Carlos',
        'last_name': 'Sainz',
        'telephone': '0739787035',
        'email': 'carlossainz@gmail.com',
        'is_active': true,
        'mobile_user_id': 'F1-7555',
        'role': 'Data Manager',
        'groups': 'Spain'
      },
    ]
  }

  static groups(){
    return [
      {
        'id': "6499c3bd-3b66-4233-a1cb-120a707c669d",
        'name': "Partner 4",
        'parent': "Uganda",
        'access_to_central_data': true,
        'permissions': [],
        'data_collectors':[]
      },
      {
        'id': "eb2fbff4-4377-4d87-bac5-c5a06e4cfc93",
        'name': "Partner 1",
        'parent': "Uganda",
        'access_to_central_data': true,
        'permissions': [],
        'data_collectors':[]
      },
      {
        'id': "895720a9-0135-4a30-beaf-0808a0cfb215",
        'name': "Uganda",
        'parent': "CRVP-Staff",
        'access_to_central_data': true,
        'permissions': [],
        'data_collectors':[]
      },
      {
        'id': "7c7aa7f1-79e2-4c3f-ab52-d5921a798e7d",
        'name': "CRVP-Staff",
        'parent': "",
        'access_to_central_data': true,
        'permissions': [],
        'data_collectors':[]
      },
    ]
  }

  static referrals(){
    return [
      {
        "id": "445ab227-15c2-4dcc-80b2-128b6289e06c",
        "date_of_referral": "01-03-2022",
        "name_of_referring_officer": "Bruno Weps",
        "name_of_client_being_referred": "Lando Norris",
        "phone_number": "0786787035",
        "date_of_birth":"02-03-1992",
        "age_category": "30 - 59 years",
        "country_of_origin": "United Kingdom",
        "identification_document": "Passport",
        "identification_number": "82634987",
        "reason_for_referral": "Requires Specialist",
        "organization_referred_to": "Finland Racing Foundation",
        "disability": "Wet Weather Racing",
        "status": "Actioned",
      },
      {
        "id": "8b7f4f4e-7eb8-4352-bcea-d26542b75928",
        "date_of_referral": "01-03-2022",
        "name_of_referring_officer": "Bruno Weps",
        "name_of_client_being_referred": "Pierre Gasly",
        "phone_number": "0786787035",
        "date_of_birth":"02-03-1992",
        "age_category": "30 - 59 years",
        "country_of_origin": "France",
        "identification_document": "Passport",
        "identification_number": "82634987",
        "reason_for_referral": "Requires Specialist",
        "organization_referred_to": "Mercedes",
        "disability": "Too Much Love for redBull",
        "status": "Actioned",
      },
      {
        "id": "cb43e2b9-de0f-42c0-a137-c2a50bc4f5af",
        "date_of_referral": "01-03-2022",
        "name_of_referring_officer": "Bruno Weps",
        "name_of_client_being_referred": "Masi",
        "phone_number": "0786787035",
        "date_of_birth":"02-03-1992",
        "age_category": "30 - 59 years",
        "country_of_origin": "Austria",
        "identification_document": "Passport",
        "identification_number": "82634987",
        "reason_for_referral": "Requires Specialist",
        "organization_referred_to": "International Sports Court",
        "disability": "redBull Support",
        "status": "Pending",
      },
      {
        "id": "e896c441-c8ba-40cf-b159-14707c0b9242",
        "date_of_referral": "01-03-2022",
        "name_of_referring_officer": "Bruno Weps",
        "name_of_client_being_referred": "Fernando Alonso",
        "phone_number": "0783977035",
        "date_of_birth":"02-03-1986",
        "age_category": "30 - 59 years",
        "country_of_origin": "Spain",
        "identification_document": "Passport",
        "identification_number": "82634987",
        "reason_for_referral": "Requires Specialist",
        "organization_referred_to": "Butabika Mental Hospital",
        "disability": "Lewis Hamilton Jealously",
        "status": "Actioned",
      },
      {
        "id": "306b06a4-cc01-4cc2-b627-ba8291f2e4a2",
        "date_of_referral": "01-03-2022",
        "name_of_referring_officer": "Bruno Weps",
        "name_of_client_being_referred": "Nikita Mazepin",
        "phone_number": "0786787035",
        "date_of_birth":"02-03-1992",
        "age_category": "30 - 59 years",
        "country_of_origin": "Russia",
        "identification_document": "Passport",
        "identification_number": "82634987",
        "reason_for_referral": "Requires Specialist",
        "organization_referred_to": "Go Karting",
        "disability": "Cant Keep A car straight",
        "status": "Pending",
      },
      {
        "id": "ca2e479b-88d0-4735-967d-fbb13c9220ed",
        "date_of_referral": "01-03-2022",
        "name_of_referring_officer": "Bruno Weps",
        "name_of_client_being_referred": "Lance Stroll",
        "phone_number": "0786787035",
        "date_of_birth":"02-03-1992",
        "age_category": "30 - 59 years",
        "country_of_origin": "Canada",
        "identification_document": "Passport",
        "identification_number": "82634987",
        "reason_for_referral": "Requires Specialist",
        "organization_referred_to": "AVSI Foundation",
        "disability": "Poor Reflex",
        "status": "Pending",
      },

    ]
  }
  static performanceReport = [
    {
      id: 'adaggfdfgsgsfgsfsd',
      output_indicators: 'No of safe spaces established within the community',
      overall_target: '500',
      cumulative_achievement: '200',
      quarter_target: '40',
      quarter_achievement: '32',
      percentage_achievement: '67%',
      comment_on_result: ''
    },
    {
      id: 'gddagdyrtaegssag',
      output_indicators: 'No of Adolescent girls utilising safe spaces',
      overall_target: '400',
      cumulative_achievement: '120',
      quarter_target: '50',
      quarter_achievement: '22',
      percentage_achievement: '70%',
      comment_on_result: ''
    }
  ];
  static financialReport = [
    {
      id: 'fdadasdasdasd',
      budget_line: 'Staff salaries and related charges',
      approved_budget: '30000',
      total_advanced: '20000',
      expense_to_date: '15000',
      quarter_expenses: '',
      variance: '4%',
      reason_for_variance: ''
    },
    {
      id: 'asdsafsagsgasgfds',
      budget_line: 'Direct support to target population',
      approved_budget: '30000',
      total_advanced: '20000',
      expense_to_date: '15000',
      quarter_expenses: '',
      variance: '25%',
      reason_for_variance: ''
    }
  ];
  static organisationalInfo = {
    id: 'asdaasrsgsdgfssgs',
    program: 'Adolescent Girl Power Program',
    cluster_organization: 'Empowered Girls of Busano',
    acronym_name: 'EGB',
    organization_type: 'CBO',
    legal_status: 'Registered NGO',
    contact_person: 'Musamali Jacob',
    physical_address: 'Khatwelatwela, Nyondo Parish',
    postal_address: 'None',
    email: 'egb@gmail.com',
    website: 'https://empoweredbusano.org/',
    country: 'Uganda',
    city: 'Mbale'
  };
  static projectInfo = {
    id: 'akhfbkabsdkfjbsjf',
    reporting_period: '22 June 2022 to 21 Sep 2022',
    grant_start_date: '21/Mar/2022',
    grant_end_date: '21/Mar/2022',
    total_grant_amount: '45,000 USD',
    amount_transferred: '12,000',
    amount_utilized: '',
    balance_spent_overspend: ''
  };
  static disbursementPlan = [
    {
      id: 'asdsadasfsagrgwtregrege',
      quarter: 'Q1',
      startDate: '21/Mar/2021',
      endDate: '21/Mar/2022',
      disbursement: 'teste'
    },
    {
      id: 'asdsadassfsdgwegrtregrege',
      quarter: 'Q2',
      startDate: '21/Jun/2021',
      endDate: '21/Jan/2022',
      disbursement: 'teset'
    },
    {
      id: 'asdsadasfertreedgregrege',
      quarter: 'Q3',
      startDate: '21/Feb/2021',
      endDate: '21/Sep/2022',
      disbursement: 'tsest'
    }
  ];
  static budget = [
    {
      id: 'asfdsfdgfsgsgfdsgfdsgfsgew',
      budgetLine: 'Staff salaries and related charges',
      approvedAmount: '5000'
    },
    {
      id: 'asfdsfdsdgfsgsgfwrqsgfsgew',
      budgetLine: 'Direct support to target population',
      approvedAmount: '15000'
    },
    {
      id: 'asfdsfdgfsgshyteywtwersgew',
      budgetLine: 'Consultants and other contracted services',
      approvedAmount: '51000'
    }
  ];
  static indicators = [
    {
      id: 'asdsfggewrwetwtrewtwtwr',
      indicator: 'No of safe spaces established within the community',
      overallTarget: '300',
      disaggregation: [
        {
          datePeriod: 'Q1',
          target: '100'
        },
        {
          datePeriod: 'Q2',
          target: '20'
        },
        {
          datePeriod: 'Q3',
          target: '80'
        },
        {
          datePeriod: 'Q4',
          target: '100'
        }
      ]
    },
    {
      id: 'asdsvnvnvgfnfhfhtwtrewtwtwr',
      indicator: 'No of Adolescent girls utilising safe spaces',
      overallTarget: '500',
      disaggregation: [
        {
          datePeriod: 'Q1',
          target: '200'
        },
        {
          datePeriod: 'Q2',
          target: '20'
        },
        {
          datePeriod: 'Q3',
          target: '180'
        },
        {
          datePeriod: 'Q4',
          target: '100'
        }
      ]
    },
  ];
  static calendar = {
    id: 'ewrwrwrwsdfdsf',
    periodType: 'quarter',
    grantStartDate: '2022-03-02T14:27',
    grantEndDate: '2025-03-02T14:27',
    projectReportingStartDate: '2022-03-02T14:27',
    reportingCalender: []
  };
  static currentStatus = {
    totalAmountDisbursed: '5000',
    totalAmountAccountedFor: '6000',
    dateOfLastDisbursement: '01/02/2022',
    startReportingCycle: 'true'
  }
  static partners = [
    {
      name: 'Partner One',
      value: 'partner_one'
    },
    {
      name: 'Parter Two',
      value: 'partner_two'
    },
    {
      name: 'Partner Three',
      value: 'partner_three'
    }
  ];
}