//
//  ViewController.m
//  mm
//
//  Created by by.huang on 2021/8/4.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UILabel *label = [[UILabel alloc]initWithFrame: CGRectMake(30, 30, 100, 100)];
    label.font = [UIFont fontWithName:@"PingFangSC-Regular" size:14.0f];
    label.text = [NSString stringWithFormat:@"渠道号：%@",[self contactsInfoFromPlistNamed:@"Channel"]];
    label.textColor = [UIColor blackColor];
    [self.view addSubview:label];
}

- (NSString *)contactsInfoFromPlistNamed:(NSString *) key {
    NSString *path = [[NSBundle mainBundle] pathForResource:@"DChannel" ofType:@"plist"];
    NSDictionary *dictionary = [NSDictionary dictionaryWithContentsOfFile:path];
    return [dictionary objectForKey:key];
}


@end
